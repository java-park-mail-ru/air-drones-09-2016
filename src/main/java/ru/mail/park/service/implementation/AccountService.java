package ru.mail.park.service.implementation;

//import jooq.airdrone.Airdrone;
import jooq.airdrone.tables.User;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;
import ru.mail.park.model.user.UserProfile;
import ru.mail.park.service.interfaces.AbstractAccountService;
import ru.mail.park.util.RequestValidator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ru.mail.park.controllers.api.exeptions.AirDroneExeptions.*;

@Component
@Transactional
public class AccountService implements AbstractAccountService {

    private final DataSource dataSource;

    @Autowired
    public AccountService(DataSource ds) {
        this.dataSource = ds;
    }


    @Override
    public UserProfile addUser(String username, String email, String password) {

        RequestValidator.emailValidate(email);
        RequestValidator.passwordValidate(password);

        Connection connection = DataSourceUtils.getConnection(dataSource);

        DSLContext create = DSL.using(connection);
        create.insertInto(User.USER)
                .columns(User.USER.EMAIL, User.USER.PASSWORD, User.USER.USERNAME)
                .values(email, password, username).execute();
        create.close();

        RequestValidator.emailValidate(email);
        RequestValidator.passwordValidate(password);

        if(username == null)
            username = "Anonymus";

        final UserProfile userProfile = new UserProfile(username, email, password);
        return userProfile;
    }

    @Override
    public UserProfile getUser(String email) {
        RequestValidator.emailValidate(email);

        Connection connection = DataSourceUtils.getConnection(dataSource);

        DSLContext create = DSL.using(connection);
        Result<Record3<String, String, String>> result =
                create.select(User.USER.EMAIL, User.USER.PASSWORD, User.USER.USERNAME).
                        from(User.USER)
                        .where(User.USER.EMAIL.equal(email))
                        .fetch();
        UserProfile userProfile = new UserProfile();

        for(Record3<String, String, String> r : result) {
            userProfile.setEmail(r.getValue(User.USER.EMAIL));
            userProfile.setPassword(r.getValue(User.USER.PASSWORD));
            userProfile.setUsername(r.getValue(User.USER.USERNAME));
        }
        create.close();

        if(userProfile.getEmail() == null)
            throw new UserNotFoundException();

        return userProfile;
    }

    @Override
    public boolean removeUser(String email, String password) throws UserBadEmailException,
                                                                        UserNotFoundException{

        RequestValidator.emailValidate(email);

        UserProfile userProfile = getUser(email);

       if(!userProfile.getPassword().equals(password))
           throw new UserPasswordsDoNotMatchException();

        Connection connection = DataSourceUtils.getConnection(dataSource);
        DSLContext create     = DSL.using(connection);
        create.delete(User.USER).where(User.USER.EMAIL.equal(email)).execute();

        return true;
    }

    @Override
    public void updateUser(String username, String email, String password, String newPassword) throws
            UserNotFoundException, UserBadEmailException, UserBadPasswordException {

        RequestValidator.emailValidate(email);
        RequestValidator.passwordValidate(password);

        UserProfile userProfile = getUser(email);
        if(userProfile == null)
            throw new UserNotFoundException();

        if(username != null) {
            userProfile.setUsername(username);
        }

        if(newPassword != null) {
            if(newPassword.equals(password))
                throw new UserPasswordsDoNotMatchException();
            RequestValidator.passwordValidate(newPassword);
            userProfile.setPassword(newPassword);
        }
    }

}
