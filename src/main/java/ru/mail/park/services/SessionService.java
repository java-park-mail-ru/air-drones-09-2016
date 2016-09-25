package ru.mail.park.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SessionService {
    private final Map<String, String> cookieToLogin = new HashMap<>();

    public String addAuthorizedLogin(String cookie, String login) {
        cookieToLogin.put(cookie, login);
        return cookie;
    }

    public String getAuthorizedLogin(String cookie) {
       return cookieToLogin.get(cookie);
    }

    public boolean removeSession(String cookie) {
        return cookieToLogin.remove(cookie) != null;
    }



}
