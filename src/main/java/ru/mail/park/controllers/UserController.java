package ru.mail.park.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.controllers.api.DeleteUserRequest;
import ru.mail.park.controllers.api.GetUserResponce;
import ru.mail.park.controllers.api.PutUserRequest;
import ru.mail.park.controllers.api.RegistrationRequest;
import ru.mail.park.model.UserProfile;
import ru.mail.park.service.implementation.AccountServiceImpl;
import ru.mail.park.service.implementation.SessionServiceImpl;
import ru.mail.park.service.interfaces.IAccountService;
import ru.mail.park.service.interfaces.ISessionService;
import ru.mail.park.util.RequestValidator;

import javax.servlet.http.HttpSession;


@RestController
public class UserController {
    private final IAccountService accountService;
    private final ISessionService sessionService;

    @Autowired
    public UserController(AccountServiceImpl accountService,
                          SessionServiceImpl sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }


    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity registration( @RequestBody  RegistrationRequest body,
                                       HttpSession httpSession) {

        final String sessionId = httpSession.getId();
        String username = body.getUsername();

        if (!RequestValidator.emailValidate(body.getEmail())
                && !RequestValidator.passwordValidate(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final UserProfile existingUser = accountService.getUser(body.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");
        }

        if(StringUtils.isEmpty(body.getUsername()))
            username = "User";

        accountService.addUser(username, body.getEmail(), body.getPassword());
        sessionService.addAuthorizedLogin(sessionId, body.getEmail());
        return ResponseEntity.ok("{OK}");
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity getUser(HttpSession httpSession) {

        final String sessionId = httpSession.getId();

        final String email = sessionService.getAuthorizedEmail(sessionId);

        if(email == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

        final UserProfile up =  accountService.getUser(email);

        return ResponseEntity.ok( new GetUserResponce(
                up.getEmail(), up.getUsername()));
    }

    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest body,
                                     HttpSession httpSession) {

        if(sessionService.getAuthorizedEmail(httpSession.getId()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        if (StringUtils.isEmpty(body.getEmail()) || StringUtils.isEmpty(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final UserProfile userProfile = accountService.getUser(body.getEmail());

        if(userProfile == null || !userProfile.getEmail().equals(body.getEmail()) ||
                !userProfile.getPassword().equals(body.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        sessionService.removeSession(httpSession.getId());
        accountService.removeUser(body.getEmail());

        return ResponseEntity.ok("{OK}");
    }

    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public ResponseEntity putUser(@RequestBody PutUserRequest body,
                                     HttpSession httpSession) {

        if(sessionService.getAuthorizedEmail(httpSession.getId()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        if (StringUtils.isEmpty(body.getEmail()) || StringUtils.isEmpty(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final UserProfile userProfile = accountService.getUser(body.getEmail());

        if(userProfile == null || !userProfile.getEmail().equals(body.getEmail()) ||
                !userProfile.getPassword().equals(body.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        if(!StringUtils.isEmpty(body.getUsername()))
            userProfile.setUsername(body.getUsername());

        final String newPassword = body.getNewPassword();
        final String oldPassword = body.getPassword();

        if(!StringUtils.isEmpty(newPassword)) {
            if (!oldPassword.equals(newPassword) &&
                    RequestValidator.passwordValidate(newPassword)) {
                userProfile.setPassword(newPassword);
            }
        }
            else ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");

        return ResponseEntity.ok("{OK}");
    }

}
