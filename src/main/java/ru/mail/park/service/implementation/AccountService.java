package ru.mail.park.service.implementation;

import jooq.airdrone.tables.User;
import org.jetbrains.annotations.Nullable;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.model.user.UserProfile;
import ru.mail.park.service.interfaces.AbstractAccountService;

import javax.sql.DataSource;
import java.sql.Connection;

import static ru.mail.park.util.RequestValidator.emailValidate;
import static ru.mail.park.util.RequestValidator.passwordValidate;

@Component
@Transactional
public class AccountService implements AbstractAccountService {


    private final DataSource dataSource;

    @Autowired
    public AccountService(DataSource ds) {
        this.dataSource = ds;
    }


    @Override
    public boolean addUser(String username, String email, String password) {

        if(!emailValidate(email) || !passwordValidate(password))
            return false;

        if(username == null)
            username = "Anonymus";

        final Connection connection = DataSourceUtils.getConnection(dataSource);

        final DSLContext create = DSL.using(connection);
        create.insertInto(User.USER)
                .columns(User.USER.EMAIL, User.USER.PASSWORD, User.USER.USERNAME)
                .values(email, password, username).execute();

        create.close();

        return true;
    }

    @Nullable
    @Override
    public UserProfile getUser(String email) {

        if(!emailValidate(email))
            return null;

        final Connection connection = DataSourceUtils.getConnection(dataSource);

        final DSLContext create = DSL.using(connection);
        final Result<Record4<String, String, String, Integer> >result =
                create.select(User.USER.EMAIL, User.USER.PASSWORD,
                        User.USER.USERNAME, User.USER.RATING).
                        from(User.USER)
                        .where(User.USER.EMAIL.equal(email))
                        .fetch();
        final UserProfile userProfile = new UserProfile();

        for(Record4<String, String, String, Integer> r : result) {
            userProfile.setEmail(r.getValue(User.USER.EMAIL));
            userProfile.setUsername(r.getValue(User.USER.USERNAME));
            userProfile.setPassword(r.getValue(User.USER.PASSWORD));
            userProfile.setRating(r.getValue(User.USER.RATING));
        }
        create.close();

        if(userProfile.getEmail() == null)
            return null;

        return userProfile;
    }

    @Override
    public boolean removeUser(String email, String password) {

        if(!emailValidate(email))
            return false;

        final UserProfile userProfile = getUser(email);

        if(userProfile == null || !userProfile.getPassword().equals(password))
            return false;

        final Connection connection = DataSourceUtils.getConnection(dataSource);
        final DSLContext create     = DSL.using(connection);
        create.delete(User.USER).where(User.USER.EMAIL.equal(email)).execute();

        return true;
    }

    @Override
    public boolean updateUser(String username, String email, String password, String newPassword)  {

        if(!emailValidate(email) || !passwordValidate(password))
            return false;

        final UserProfile userProfile = getUser(email);

        if(userProfile == null)
            return false;

        if(username != null) {
            userProfile.setUsername(username);
        }

        if(newPassword != null) {
            if(!passwordValidate(newPassword) || newPassword.equals(password))
                return false;
            userProfile.setPassword(newPassword);
        }

        final Connection connection = DataSourceUtils.getConnection(dataSource);
        final DSLContext create     = DSL.using(connection);
        create.update(User.USER)
                .set(User.USER.PASSWORD, userProfile.getPassword())
                .set(User.USER.USERNAME, userProfile.getUsername())
                .where(User.USER.EMAIL.equal(userProfile.getEmail())).execute();

        create.close();

        return true;
    }

}
