package ru.mail.park.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mail.park.model.user.UserProfile;
import ru.mail.park.service.interfaces.AbstractAccountService;
import ru.mail.park.service.interfaces.AbstractSessionService;
import ru.mail.park.util.RequestValidator;

import java.util.HashMap;
import java.util.Map;

import static ru.mail.park.controllers.api.exeptions.AirDroneExeptions.*;


@Component
public class SessionService implements AbstractSessionService {
    private final Map<String, String> cookieToEmail = new HashMap<>();

    private final AbstractAccountService accountService;

    @Autowired
    public SessionService(AbstractAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void signIn(String cookie, String email, String password) throws
            UserPasswordsDoNotMatchException, UserBadEmailException, UserNotFoundException {

        RequestValidator.emailValidate(email);
        final UserProfile userProfile = accountService.getUser(email);
        if( !userProfile.getPassword().equals(password))
            throw new UserPasswordsDoNotMatchException();
        cookieToEmail.put(cookie, email);
    }

    @Override
    public String getAuthorizedEmail(String cookie) throws  NotLoggedInException {
        final String email = cookieToEmail.get(cookie);
        if(email == null)
            throw new NotLoggedInException();
        return cookieToEmail.get(cookie);
    }

    @Override
    public void signOut(String cookie) throws NotLoggedInException {
        if(cookieToEmail.remove(cookie) == null)
            throw new NotLoggedInException();
    }

//    public void

}
