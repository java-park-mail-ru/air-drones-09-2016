package ru.mail.park.controllers.api;

import ru.mail.park.controllers.api.abstractapi.AbstractRequest;

/**
 * Created by admin on 02.10.16.
 */

public final class SignInRequest extends AbstractRequest {

    private SignInRequest() {}

    private SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
