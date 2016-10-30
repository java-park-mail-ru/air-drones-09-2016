package ru.mail.park.controllers.api;


/**
 * Created by admin on 06.10.16.
 */
public  final class DeleteUserRequest {

    private String email;
    private String password;

    public DeleteUserRequest() {}

    public DeleteUserRequest(String email, String password) {
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