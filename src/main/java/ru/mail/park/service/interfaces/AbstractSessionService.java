package ru.mail.park.service.interfaces;

import org.springframework.stereotype.Component;

/**
 * Created by admin on 30.09.16.
 */
@Component
public interface AbstractSessionService {

    boolean signIn(String cookie, String email, String password);

    String getAuthorizedEmail(String cookie);

    boolean signOut(String cookie);
}
