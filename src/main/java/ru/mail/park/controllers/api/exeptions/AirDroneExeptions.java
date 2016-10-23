package ru.mail.park.controllers.api.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by admin on 22.10.16.
 */
public class AirDroneExeptions {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class NotLoggedInException extends RuntimeException {
        @Override
        public String getMessage() {
            return "NotLogged";
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UserNotFoundException extends RuntimeException {
        @Override
        public String getMessage() {
            return "UserNotFound";
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class UserBadEmailException extends RuntimeException {
        @Override
        public String getMessage() {
            return "UserBadEmail";
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class UserExistEmailException extends RuntimeException {
        @Override
        public String getMessage() {
            return "UserExistEmail";
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class UserBadPasswordException extends RuntimeException {
        @Override
        public String getMessage() {
            return "UserBadPassword";
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class UserOldNewPasswordEqualsException extends RuntimeException {
        @Override
        public String getMessage() {
            return "UserOldNewPassword";
        }
    }

}