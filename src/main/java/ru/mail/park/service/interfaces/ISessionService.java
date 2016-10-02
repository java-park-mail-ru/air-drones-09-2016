package ru.mail.park.service.interfaces;

/**
 * Created by admin on 30.09.16.
 */
public interface ISessionService {
    String addAuthorizedLogin(String cookie, String email);

    String getAuthorizedEmail(String cookie);

    boolean removeSession(String cookie);
}
