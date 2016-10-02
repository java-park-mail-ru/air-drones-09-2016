package ru.mail.park.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.implementation.AccountServiceImpl;
import ru.mail.park.services.implementation.SessionServiceImpl;
import ru.mail.park.controllers.registration.requests.RegistraionReqResp;

import javax.servlet.http.HttpSession;


@RestController
public class RegistrationController {
    private final AccountServiceImpl accountService;
    private final SessionServiceImpl sessionService;

    @Autowired
    public RegistrationController(AccountServiceImpl accountService, SessionServiceImpl sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }


//    Метод создания пользователя
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody RegistraionReqResp.RegistrationRequest body,
                                HttpSession httpSession) {

        final String sessionId = httpSession.getId();

        String username = body.getUsername();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");
        }

        final UserProfile existingUser = accountService.getUser(email);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");
        }

        if(StringUtils.isEmpty(username))
            username = "User";

        accountService.addUser(username, email, password);
        sessionService.addAuthorizedLogin(sessionId, email);
        return ResponseEntity.ok(new RegistraionReqResp.AutorizedSessionResponce(httpSession.getId()));
    }

//    Метод получения информации о пользователе
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity getUser(HttpSession httpSession) {

        String sessionId = httpSession.getId();

        final String email = sessionService.getAuthorizedEmail(sessionId);

        if(email == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

        return ResponseEntity.ok(accountService.getUser(email));
    }

//    Метод удаления пользователя
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody RegistraionReqResp.DeleteRequest body,
                                     HttpSession httpSession) {

        if(sessionService.getAuthorizedEmail(httpSession.getId()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        if (StringUtils.isEmpty(body.getEmail()) || StringUtils.isEmpty(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final UserProfile userProfile = accountService.getUser(body.getEmail());

        if(userProfile == null || !userProfile.getPassword().equals(body.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        sessionService.removeSession(httpSession.getId());
        accountService.removeUser(body.getEmail());

        return ResponseEntity.ok("{OK}");
    }

}
