package ru.mail.park.controllers.api;


/**
 * Created by admin on 02.10.16.
 */
public final class RegistrationRequest {

    private String username;
    private String password;
    private String email;

    public RegistrationRequest() {}

    public RegistrationRequest(String username, String email,
                                String password) {
        this.username     = username;
        this.email        = email;
        this.password     = password;
    }

    public String getUsername() {
            return username;
        }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
