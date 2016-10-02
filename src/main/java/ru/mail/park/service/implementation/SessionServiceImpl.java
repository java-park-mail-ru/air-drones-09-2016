package ru.mail.park.service.implementation;

import org.springframework.stereotype.Service;
import ru.mail.park.service.interfaces.ISessionService;

import java.util.HashMap;
import java.util.Map;


@Service
public class SessionServiceImpl implements ISessionService {
    private static final Map<String, String> COOKIE_TO_EMAIL = new HashMap<>();

    @Override
    public String addAuthorizedLogin(String cookie, String email) {
        COOKIE_TO_EMAIL.put(cookie, email);
        return cookie;
    }

    @Override
    public String getAuthorizedEmail(String cookie) {
       return COOKIE_TO_EMAIL.get(cookie);
    }

    @Override
    public boolean removeSession(String cookie) {
        return COOKIE_TO_EMAIL.remove(cookie) != null;
    }


}
