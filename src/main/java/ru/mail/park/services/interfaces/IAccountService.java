package ru.mail.park.services.interfaces;

import ru.mail.park.model.UserProfile;

/**
 * Created by admin on 30.09.16.
 */
public interface IAccountService {
    public UserProfile addUser(String username, String email, String password);

    public UserProfile getUser(String email);

    public boolean removeUser(String email);
}
