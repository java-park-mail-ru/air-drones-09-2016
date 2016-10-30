package ru.mail.park.model.user;

import org.junit.Test;
import ru.mail.park.model.user.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by admin on 22.10.16.
 */
public class UserProfileTest {

    @Test
    public void testCreateUserProfile() {
        final UserProfile userProfile = new UserProfile("username", "user@mail.ru", "123456789");
        assertEquals(userProfile.getEmail(), "user@mail.ru");
        assertEquals(userProfile.getUsername(), "username");
        assertEquals(userProfile.getPassword(), "123456789");
    }

}