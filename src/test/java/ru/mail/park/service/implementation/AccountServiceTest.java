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
import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;
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

    @Test(expected = AirDroneExeptions.UserNotFoundException.class)
    public void addUserNotFound() {
        accountService.getUser("userusqfer@mail.ru");
    }

    @Test(expected = AirDroneExeptions.UserBadEmailException.class)
    public void userNotValidEmailEx() {
        accountService.addUser("user", "usermail.ru", "kmweiNIBQFIUb278h");
    }

    @Test(expected = AirDroneExeptions.UserBadPasswordException.class)
    public void userBadPasswordEx() {
        accountService.addUser("user", "user@mail.ru", "kmw2F");
    }

    @Test(expected = AirDroneExeptions.UserNotFoundException.class)
    public void removeUser() throws Exception {
        accountService.addUser("user", "user@mail.ru", "kmweiNIBQFIUb278h");
        accountService.removeUser("user@mail.ru", "kmweiNIBQFIUb278h");
        accountService.getUser("user@mail.ru");
    }

    @Test
    public void updateUser() throws Exception {
        accountService.addUser("user", "user@mail.ru", "kmweiNIBQFIUb278h");
        accountService.updateUser("user1", "user@mail.ru", "kmweiNIBQFIUb278h", "newPassword10");
        final UserProfile userProfile = accountService.getUser("user@mail.ru");
        assertEquals(userProfile.getEmail(), "user@mail.ru");
        assertEquals(userProfile.getUsername(), "user1");
        assertEquals(userProfile.getPassword(), "newPassword10");
    }

}