package ru.mail.park.service.implementation;

import org.springframework.stereotype.Component;
import ru.mail.park.model.UserProfile;
import ru.mail.park.service.interfaces.IAccountService;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountServiceImpl implements IAccountService {
    private final Map<String, UserProfile> userNameToUser = new HashMap<>();

    @Override
    public UserProfile addUser(String username, String email, String password) {
        final UserProfile userProfile = new UserProfile(username, email, password);
        userNameToUser.put(email, userProfile);
        return userProfile;
    }
    @Override
    public UserProfile getUser(String email) {
        return userNameToUser.get(email);
    }
    @Override
    public boolean removeUser(String email) {
        return userNameToUser.remove(email) != null;
    }
}
