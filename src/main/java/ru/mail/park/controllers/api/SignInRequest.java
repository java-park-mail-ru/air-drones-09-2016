package ru.mail.park.controllers.api;


/**
 * Created by admin on 02.10.16.
 */

public final class SignInRequest {

    private String email;
    private String password;

    public SignInRequest() {}

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
