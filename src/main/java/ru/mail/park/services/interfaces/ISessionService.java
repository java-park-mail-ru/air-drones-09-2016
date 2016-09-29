package ru.mail.park.services.interfaces;

/**
 * Created by admin on 30.09.16.
 */
public interface ISessionService {
    public String addAuthorizedLogin(String cookie, String email);

    public String getAuthorizedEmail(String cookie);

    public boolean removeSession(String cookie);
}
