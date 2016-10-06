package ru.mail.park.controllers.api;

import ru.mail.park.controllers.api.abstractapi.AbstractRequest;


/**
 * Created by admin on 02.10.16.
 */
public final class RegistrationRequest extends AbstractRequest {

    private String username;

    private RegistrationRequest() {}

    private RegistrationRequest(String username, String email,
                                String password) {
        this.username     = username;
        this.email        = email;
        this.password     = password;
    }

    public String getUsername() {
            return username;
        }

}
