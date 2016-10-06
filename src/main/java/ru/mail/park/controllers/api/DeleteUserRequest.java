package ru.mail.park.controllers.api;

import ru.mail.park.controllers.api.abstractapi.AbstractRequest;

/**
 * Created by admin on 06.10.16.
 */
public  final class DeleteUserRequest extends AbstractRequest{

    private DeleteUserRequest() {}

    public DeleteUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}