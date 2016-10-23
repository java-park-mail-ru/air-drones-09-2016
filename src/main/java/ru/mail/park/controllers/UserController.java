package ru.mail.park.controllers;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.controllers.api.DeleteUserRequest;
import ru.mail.park.controllers.api.GetUserResponce;
import ru.mail.park.controllers.api.PutUserRequest;
import ru.mail.park.controllers.api.RegistrationRequest;
import ru.mail.park.controllers.api.common.ResultJson;
import ru.mail.park.controllers.api.exeptions.AirDroneExeptions.*;
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
            UserProfile userProfile = accountService.addUser(username, email, password);
            sessionService.signIn(sessionId, email, body.getPassword());
            userProfile.setPassword(null);
            return ResponseEntity.status(HttpStatus.OK).body((new ResultJson<UserProfile>(
                    HttpStatus.OK.value(), userProfile)).getStringResult());
        } catch (DataAccessException e) {

            String errJson = (new ResultJson<String>( HttpStatus.FORBIDDEN.value(),
                                                    e.getClass().getName())).getStringResult();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errJson);
        }
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET,
                                            produces = "application/json")
    public ResponseEntity getUser(HttpSession httpSession) {

        try {
            final String sessionId = httpSession.getId();
            final String email = sessionService.getAuthorizedEmail(sessionId);

            final UserProfile userProfile = accountService.getUser(email);
            userProfile.setPassword(null);

            return ResponseEntity.status(HttpStatus.OK).body(new ResultJson<UserProfile>(
                    HttpStatus.OK.value(), userProfile).getStringResult());

        } catch (NotLoggedInException e) {
            String errJson = (new ResultJson<String>( HttpStatus.FORBIDDEN.value(),
                                                        e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errJson);
        }
    }

    @RequestMapping(path = "/user", method = RequestMethod.DELETE,
                                                produces = "application/json")
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest body,
                                     HttpSession httpSession) {

        try {
            accountService.removeUser(body.getEmail(), body.getPassword());
            sessionService.signOut(httpSession.getId());

        } catch (UserBadEmailException e) {
            String errJson = (new ResultJson<String>( HttpStatus.BAD_REQUEST.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errJson);

        } catch (UserNotFoundException e) {
            String errJson = (new ResultJson<String>( HttpStatus.NOT_FOUND.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errJson);
        }

        String json = (new ResultJson<String>( HttpStatus.OK.value(), "OK")).getStringResult();
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping(path = "/user", method = RequestMethod.PUT,
                                        produces = "application/json")
    public ResponseEntity putUser(@RequestBody PutUserRequest body,
                                     HttpSession httpSession) {

        try {
            sessionService.getAuthorizedEmail(httpSession.getId());
            accountService.updateUser(body.getUsername(), body.getEmail(),
                    body.getPassword(), body.getNewPassword());
        } catch (NotLoggedInException e) {
            String errJson = (new ResultJson<String>( HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errJson);
        } catch (UserPasswordsDoNotMatchException e) {
            String errJson = (new ResultJson<String>( HttpStatus.BAD_REQUEST.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errJson);
        } catch (UserBadEmailException e) {
            String errJson = (new ResultJson<String>( HttpStatus.BAD_REQUEST.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errJson);
        }


        String json = (new ResultJson<String>( HttpStatus.OK.value(), "OK")).getStringResult();
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


    @ExceptionHandler({CannotCreateTransactionException.class})
    @ResponseBody
    public ResponseEntity resolveIOException() {
        String errJson = new ResultJson<String>( HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "DataBaseUnavailible").getStringResult();
        return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(errJson);
    }

}
