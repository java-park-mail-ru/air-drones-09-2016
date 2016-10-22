package ru.mail.park.controllers.api.abstractapi;

/**
 * Created by admin on 06.10.16.
 */
public abstract class AbstractRequest {

    protected String email;
    protected String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
