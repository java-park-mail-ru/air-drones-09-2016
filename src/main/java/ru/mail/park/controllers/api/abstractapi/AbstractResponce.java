package ru.mail.park.controllers.api.abstractapi;

/**
 * Created by admin on 06.10.16.
 */
public abstract class AbstractResponce {
    protected String email;
    protected String username;

    public String getEmail() {return email;}

    public String getUsername() { return username; }
}
