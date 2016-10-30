package ru.mail.park.controllers;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.controllers.api.DeleteUserRequest;
import ru.mail.park.controllers.api.PutUserRequest;
import ru.mail.park.controllers.api.RegistrationRequest;
import ru.mail.park.controllers.api.common.ResultJson;
import ru.mail.park.model.user.UserProfile;
import ru.mail.park.service.interfaces.AbstractAccountService;
import ru.mail.park.service.interfaces.AbstractSessionService;

import javax.servlet.http.HttpSession;


@RestController
public class UserController {
    private final AbstractAccountService accountService;
    private final AbstractSessionService sessionService;

    @Autowired
    public UserController(AbstractAccountService accountService,
                          AbstractSessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }


    @RequestMapping(path = "/user", method = RequestMethod.POST,
                                        produces = "application/json")
    public ResponseEntity registration( @RequestBody  RegistrationRequest body,
                                       HttpSession httpSession) {

        final String sessionId = httpSession.getId();
        final String username = body.getUsername();
        final String email    = body.getEmail();
        final String password = body.getPassword();

        try{
            if(!accountService.addUser(username, email, password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((new ResultJson<>(
                        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST)).getStringResult());
            }
            sessionService.signIn(sessionId, email, body.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body((new ResultJson<>(
                    HttpStatus.OK.value(), HttpStatus.OK)).getStringResult());
        } catch (DataAccessException e) {

            final String errJson;

            if(e.sqlState().equals("23505")) {
                errJson = (new ResultJson<>(HttpStatus.FORBIDDEN.value(),
                        "unique_violation")).getStringResult();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errJson);
            }

            errJson = (new ResultJson<>(HttpStatus.FORBIDDEN.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errJson);
        }
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET,
                                            produces = "application/json")
    public ResponseEntity getUser(HttpSession httpSession) {

            final String sessionId = httpSession.getId();

            final String email = sessionService.getAuthorizedEmail(sessionId);

            if(email == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResultJson<>(
                        HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED).getStringResult());

            final UserProfile userProfile = accountService.getUser(email);

            return ResponseEntity.status(HttpStatus.OK).body(new ResultJson<>(
                    HttpStatus.OK.value(), userProfile).getStringResult());

    }

    @RequestMapping(path = "/user", method = RequestMethod.DELETE,
                                                produces = "application/json")
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest body,
                                     HttpSession httpSession) {

            final String email = sessionService.getAuthorizedEmail(httpSession.getId());

            if(email == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResultJson<>(
                        HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED).getStringResult());

            if(!accountService.removeUser(email, body.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultJson<>(
                        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST).getStringResult());
            }
            sessionService.signOut(httpSession.getId());

        final String json = (new ResultJson<>(HttpStatus.OK.value(), HttpStatus.OK)).getStringResult();
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping(path = "/user", method = RequestMethod.PUT,
                                        produces = "application/json")
    public ResponseEntity putUser(@RequestBody PutUserRequest body,
                                     HttpSession httpSession) {

        final String email = sessionService.getAuthorizedEmail(httpSession.getId());

        if(email == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResultJson<>(
                    HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED).getStringResult());

        if(!accountService.updateUser(body.getUsername(), body.getEmail(),
                body.getPassword(), body.getNewPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultJson<>(
                    HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST).getStringResult());

        final String json = (new ResultJson<>(HttpStatus.OK.value(),
                        HttpStatus.OK)).getStringResult();
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


    @ExceptionHandler({CannotCreateTransactionException.class})
    @ResponseBody
    public ResponseEntity resolveIOException() {
        final String errJson = new ResultJson<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "DataBaseUnavailible").getStringResult();
        return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(errJson);
    }

    @ExceptionHandler({DataAccessException.class})
    @ResponseBody
    public ResponseEntity dataAccessException() {
        final String errJson = new ResultJson<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "DataBaseUnavailible").getStringResult();
        return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(errJson);
    }

}
