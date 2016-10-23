package ru.mail.park.service.interfaces;

import org.springframework.stereotype.Component;
import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;

/**
 * Created by admin on 30.09.16.
 */
@Component
public interface AbstractSessionService {

    void signIn(String cookie, String email, String password) throws
            AirDroneExeptions.UserPasswordsDoNotMatchException,
            AirDroneExeptions.UserBadEmailException, AirDroneExeptions.UserNotFoundException ;

    String getAuthorizedEmail(String cookie) throws  AirDroneExeptions.NotLoggedInException;

    void signOut(String cookie) throws AirDroneExeptions.NotLoggedInException;
}
