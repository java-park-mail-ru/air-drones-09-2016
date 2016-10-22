package ru.mail.park.controllers.api;

import ru.mail.park.controllers.api.abstractapi.AbstractResponce;

/**
 * Created by admin on 06.10.16.
 */
public final class GetUserResponce extends AbstractResponce{

    private GetUserResponce() {}

    public GetUserResponce(String email, String username) {
        this.email = email;
        this.username = username;
    }

}

