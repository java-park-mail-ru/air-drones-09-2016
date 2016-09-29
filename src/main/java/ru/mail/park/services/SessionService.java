package ru.mail.park.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SessionService {
    private final static  Map<String, String> cookieToEmail = new HashMap<>();

    public String addAuthorizedLogin(String cookie, String email) {
        cookieToEmail.put(cookie, email);
        return cookie;
    }

    public String getAuthorizedEmail(String cookie) {
       return cookieToEmail.get(cookie);
    }

    public boolean removeSession(String cookie) {
        return cookieToEmail.remove(cookie) != null;
    }


}
