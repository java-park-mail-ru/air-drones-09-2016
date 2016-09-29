package ru.mail.park.services.implementation;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.interfaces.IAccountService;

import java.util.HashMap;
import java.util.Map;

@Service
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
