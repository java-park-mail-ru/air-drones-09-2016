package ru.mail.park.services.implementation;

import org.springframework.stereotype.Service;
import ru.mail.park.services.interfaces.ISessionService;

import java.util.HashMap;
import java.util.Map;


@Service
public class SessionServiceImpl implements ISessionService {
    private final static  Map<String, String> cookieToEmail = new HashMap<>();

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
