package ru.mail.park.service.implementation;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mail.park.model.user.UserProfile;
import ru.mail.park.service.interfaces.AbstractAccountService;
import ru.mail.park.service.interfaces.AbstractSessionService;
import static ru.mail.park.util.RequestValidator.emailValidate;
import static ru.mail.park.util.RequestValidator.passwordValidate;

import java.util.HashMap;
import java.util.Map;



@Component
public class SessionService implements AbstractSessionService {
    private final Map<String, String> cookieToEmail = new HashMap<>();

    private final AbstractAccountService accountService;

    @Autowired
    public SessionService(AbstractAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean signIn(String cookie, String email, String password)  {

        if(!emailValidate(email) || !passwordValidate(password))
            return false;

        final UserProfile userProfile = accountService.getUser(email);

        if(userProfile == null)
            return false;

        if( !userProfile.getPassword().equals(password))
            return false;

        cookieToEmail.put(cookie, email);

        return true;
    }

    @Nullable
    @Override
    public String getAuthorizedEmail(String cookie)  {
        final String email = cookieToEmail.get(cookie);
        if(email == null)
            return null;
        return email;
    }

    @Override
    public boolean signOut(String cookie)  {
        return cookieToEmail.remove(cookie) != null;
    }

}
