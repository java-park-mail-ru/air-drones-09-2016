package ru.mail.park.controllers.api;


/**
 * Created by admin on 06.10.16.
 */
public final class PutUserRequest {

    private String username;
    private String newPassword;
    private String email;
    private String password;

    public PutUserRequest() {}

    public PutUserRequest(String email, String password, String username, String newPassword) {
        this.email       = email;
        this.password    = password;
        this.username    = username;
        this.newPassword = newPassword;
    }

    public String getUsername() {return username;}

    public String getNewPassword() {return newPassword;}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
