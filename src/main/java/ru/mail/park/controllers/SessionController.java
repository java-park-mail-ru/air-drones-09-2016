package ru.mail.park.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.controllers.entities.SessionReqResp;
import ru.mail.park.model.UserProfile;
import ru.mail.park.service.implementation.AccountServiceImpl;
import ru.mail.park.service.implementation.SessionServiceImpl;
import ru.mail.park.service.interfaces.IAccountService;
import ru.mail.park.service.interfaces.ISessionService;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {

    private final IAccountService accountService;
    private final ISessionService sessionService;

    @Autowired
    public SessionController(AccountServiceImpl accountService,
                             SessionServiceImpl sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    //    Метод логина пользователя
    @RequestMapping(path = "/session", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody SessionReqResp.SignInRequest body,
                                HttpSession httpSession) {

        if (StringUtils.isEmpty(body.getEmail())
                || StringUtils.isEmpty(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final UserProfile user = accountService.getUser(body.getEmail());

        if(user == null || !user.getPassword().equals(body.getPassword())
                ||  !user.getEmail().equals(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        sessionService.addAuthorizedLogin(httpSession.getId(), body.getEmail());

        return ResponseEntity.ok(new SessionReqResp.AutorizedSessionResponse(httpSession.getId()));
    }

    //    Метод логаута пользователя
    @RequestMapping(path = "/session", method = RequestMethod.DELETE)
    public ResponseEntity signOut(HttpSession httpSession) {

        if(sessionService.removeSession(httpSession.getId()))
            return ResponseEntity.ok("{OK}");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");

    }
}
