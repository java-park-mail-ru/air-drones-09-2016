package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.AccountService;
import ru.mail.park.services.SessionService;

import javax.servlet.http.HttpSession;


@RestController
public class RegistrationController {
    private final AccountService accountService;
    private final SessionService sessionService;

    @Autowired
    public RegistrationController(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }


//    Метод создания пользователя
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody RegistrationRequest body,
                                HttpSession httpSession) {
        final String sessionId = httpSession.getId();
        System.err.println(sessionId);

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();
        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");
        }
        final UserProfile existingUser = accountService.getUser(login);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");
        }

        accountService.addUser(login, password, email);
        sessionService.addAuthorizedLogin(sessionId, login);
        return ResponseEntity.ok(new AutorizedSession(httpSession.getId()));
    }

//бэкдор с ID
//    Метод получения информации о пользователе
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity getUser(@RequestParam(name = "id") String id) {

//        final String ID = body.getSessionId();
        if (StringUtils.isEmpty(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final String login = sessionService.getAuthorizedLogin(id);

        if(login == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

        return ResponseEntity.ok(accountService.getUser(login));
    }

//    Метод удаления пользователя
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody DeleteRequest body) {

        if (StringUtils.isEmpty(body.getLogin()) || StringUtils.isEmpty(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        final UserProfile userProfile = accountService.getUser(body.getLogin());

        if(userProfile == null || !userProfile.getPassword().equals(body.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");

        accountService.removeUser(body.getLogin());

        return ResponseEntity.ok("{OK}");
    }

    private static final class RegistrationRequest {
        private String login;
        private String password;
        private String email;

        private RegistrationRequest() {}

        private RegistrationRequest(String login, String password, String email) {
            this.login = login;
            this.password = password;
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }
    }

    private static final class SuccessResponse {
        private final String login;

        private SuccessResponse(String login) {
            this.login = login;
        }

        @SuppressWarnings("unused")
        public String getLogin() {
            return login;
        }
    }

    private static  final class AutorizedSession {
        private final String sessionId;

        private AutorizedSession(String sessionId) {this.sessionId = sessionId;}

        public String getSessionId() {return  sessionId;}
    }

    private static final class DeleteRequest {
        private String login;
        private String password;

        private DeleteRequest() {}

        private DeleteRequest(String login, String password) {
            this.login    = login;
            this.password = password;
        }

        public String getLogin() {return login;}

        public String getPassword() {return password;}

    }

}
