package ru.mail.park.service.interfaces;

import ru.mail.park.model.UserProfile;

/**
 * Created by admin on 30.09.16.
 */
public interface IAccountService {
    UserProfile addUser(String username, String email, String password);

    UserProfile getUser(String email);

    boolean removeUser(String email);
}
