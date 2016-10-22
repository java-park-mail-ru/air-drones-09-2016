package ru.mail.park.controllers.api;

import ru.mail.park.controllers.api.abstractapi.AbstractRequest;

/**
 * Created by admin on 06.10.16.
 */
public final class PutUserRequest extends AbstractRequest {

    private String username;
    private String newPassword;

    private PutUserRequest() {}

    private PutUserRequest(String email, String password, String username, String newPassword) {
        this.email       = email;
        this.password    = password;
        this.username    = username;
        this.newPassword = newPassword;
    }

    public String getUsername() {return username;}

    public String getNewPassword() {return newPassword;}

}
