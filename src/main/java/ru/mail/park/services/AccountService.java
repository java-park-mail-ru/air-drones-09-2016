package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private final Map<String, UserProfile> userNameToUser = new HashMap<>();

    public UserProfile addUser(String login, String password, String email) {
        final UserProfile userProfile = new UserProfile(login, email, password);
        userNameToUser.put(login, userProfile);
        return userProfile;
    }

    public UserProfile getUser(String login) {
        return userNameToUser.get(login);
    }

    public boolean removeUser(String login) {
        return userNameToUser.remove(login) != null;
    }
}
