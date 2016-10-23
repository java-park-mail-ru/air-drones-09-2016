package ru.mail.park.util;

import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;

/**
 * Created by admin on 06.10.16.
 */
public class RequestValidator {

    private static final String EMAIL_RULE = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
            "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
            "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|" +
            "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@" +
            "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
            "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|" +
            "[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
            "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private static final String PASSWORD_RULE = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    public static boolean emailValidate(String email) throws
                                AirDroneExeptions.UserBadEmailException{
        if(email == null || !email.matches(EMAIL_RULE))
            throw new AirDroneExeptions.UserBadEmailException();
        return true;
    }

    public static boolean passwordValidate(String password) throws
                                AirDroneExeptions.UserBadPasswordException{
        if(password == null || !password.matches(PASSWORD_RULE))
            throw new AirDroneExeptions.UserBadPasswordException();
        return true;
    }


}
