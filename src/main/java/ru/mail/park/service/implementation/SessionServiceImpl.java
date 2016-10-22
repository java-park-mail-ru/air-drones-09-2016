package ru.mail.park.service.implementation;

import org.springframework.stereotype.Component;
import ru.mail.park.service.interfaces.ISessionService;

import java.util.HashMap;
import java.util.Map;


@Component
public class SessionServiceImpl implements ISessionService {
    private final Map<String, String> cookieToEmail = new HashMap<>();

    @Override
    public String addAuthorizedLogin(String cookie, String email) {
        cookieToEmail.put(cookie, email);
        return cookie;
    }

    @Override
    public String getAuthorizedEmail(String cookie) {
       return cookieToEmail.get(cookie);
    }

    @Override
    public boolean removeSession(String cookie) {
        return cookieToEmail.remove(cookie) != null;
    }

}
