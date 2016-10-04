package ru.mail.park.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.controllers.entities.RegistraionReqResp;
import ru.mail.park.model.UserProfile;
import ru.mail.park.service.implementation.AccountServiceImpl;
import ru.mail.park.service.implementation.SessionServiceImpl;
import ru.mail.park.service.interfaces.IAccountService;
import ru.mail.park.service.interfaces.ISessionService;

import javax.servlet.http.HttpSession;


@RestController
public class RegistrationController {
    private final IAccountService accountService;
    private final ISessionService sessionService;

    @Autowired
    public RegistrationController(AccountServiceImpl accountService,
                                  SessionServiceImpl sessionService) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
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

        final String sessionId = httpSession.getId();

        final String email = sessionService.getAuthorizedEmail(sessionId);

        if(email == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

        final UserProfile up =  accountService.getUser(email);

        return ResponseEntity.ok( new RegistraionReqResp.GetUserResponce(
                up.getEmail(), up.getUsername())
                /*accountService.getUser(email)*/);
    }

    //    Метод удаления пользователя
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody RegistraionReqResp.UserReqResp body,
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
    //    Метод изменения данных пользователя
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public ResponseEntity putUser(@RequestBody RegistraionReqResp.PutUserRequest  body,
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

        if(!StringUtils.isEmpty(body.getNewPassword()))
            userProfile.setPassword(body.getNewPassword());

        return ResponseEntity.ok("{OK}");
    }

}
