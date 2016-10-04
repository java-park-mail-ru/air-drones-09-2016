package ru.mail.park.service.interfaces;

import org.springframework.stereotype.Component;

/**
 * Created by admin on 30.09.16.
 */
@Component
public interface ISessionService {
    String addAuthorizedLogin(String cookie, String email);

    String getAuthorizedEmail(String cookie);

    boolean removeSession(String cookie);
}
