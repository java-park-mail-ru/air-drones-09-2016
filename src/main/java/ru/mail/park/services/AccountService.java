package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private final Map<String, UserProfile> userNameToUser = new HashMap<>();

    public UserProfile addUser(String username, String email, String password) {
        final UserProfile userProfile = new UserProfile(username, email, password);
        userNameToUser.put(email, userProfile);
        return userProfile;
    }

    public UserProfile getUser(String email) {
        return userNameToUser.get(email);
    }

    public boolean removeUser(String email) {
        return userNameToUser.remove(email) != null;
    }
}
