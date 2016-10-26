package ru.mail.park.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.model.user.UserProfile;
import ru.mail.park.service.interfaces.AbstractAccountService;

import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * Created by admin on 23.10.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class AccountServiceTest {
    private AbstractAccountService accountService;


    @Autowired
    private DataSource dataSource;


    @Before
    public void init() {
            accountService = new AccountService(dataSource);
    }


    @Test
    public void addUser() throws Exception {
        accountService.addUser("user", "user@mail.ru", "kmweiNIBQFIUb278h");
        final UserProfile userProfile = accountService.getUser("user@mail.ru");
        assertEquals(userProfile.getEmail(), "user@mail.ru");
        assertEquals(userProfile.getUsername(), "user");
        assertEquals(userProfile.getPassword(), "kmweiNIBQFIUb278h");
    }

    @Test
    public void getUserTest() {

        assertEquals(accountService.getUser("userusqfer@mail.ru"), null);

        accountService.addUser("user", "user@mail.ru", "kmweiNIBQFIUb278h");
        final UserProfile userProfile = accountService.getUser("user@mail.ru");
        assertEquals(userProfile.getEmail(), "user@mail.ru");
        assertEquals(userProfile.getUsername(), "user");
        assertEquals(userProfile.getPassword(), "kmweiNIBQFIUb278h");
    }

    @Test
    public void userNotValid() {

        assertEquals(accountService.addUser("user", "usermail.ru", "kmweiNIBQFIUb278h"), false);
        assertEquals(accountService.addUser("user", "user@mail.ru", "kmw2F"), false);
    }


    public void removeUser()  {
        accountService.addUser("user", "user@mail.ru", "kmweiNIBQFIUb278h");
        accountService.removeUser("user@mail.ru", "kmweiNIBQFIUb278h");
        assertEquals(accountService.getUser("user@mail.ru"), null);
    }

    @Test
    public void updateUser()  {
        assertEquals(accountService.addUser("user", "user@mail.ru", "kmweiNIBQFIUb278h"), true);
        assertEquals(accountService
                .updateUser("user1", "user@mail.ru", "kmweiNIBQFIUb278h", "newPassword10"), true);
        final UserProfile userProfile = accountService.getUser("user@mail.ru");
        assertEquals(userProfile.getEmail(), "user@mail.ru");
        assertEquals(userProfile.getUsername(), "user1");
        assertEquals(userProfile.getPassword(), "newPassword10");
    }

}