package ru.mail.park.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.controllers.api.SignInRequest;
import ru.mail.park.controllers.api.common.ResultJson;
import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;
import ru.mail.park.service.interfaces.AbstractSessionService;

import javax.servlet.http.HttpSession;

import static ru.mail.park.controllers.api.exeptions.AirDroneExeptions.*;

@RestController
public class SessionController {

    private final AbstractSessionService sessionService;

    @Autowired
    public SessionController(AbstractSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(path = "/session", method = RequestMethod.POST,
                                            produces = "application/json")
    public ResponseEntity signIn(@RequestBody SignInRequest body,
                                HttpSession httpSession) {
        try {
            sessionService.signIn(httpSession.getId(), body.getEmail(), body.getPassword());

        } catch (AirDroneExeptions.UserBadEmailException e) {
            final String errJson = (new ResultJson<>(HttpStatus.FORBIDDEN.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errJson);

        } catch (UserNotFoundException e) {

            final String errJson = (new ResultJson<>(HttpStatus.NOT_FOUND.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errJson);

        } catch (UserPasswordsDoNotMatchException e) {
            final String errJson = (new ResultJson<>(HttpStatus.FORBIDDEN.value(),
                    e.getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errJson);
        }

        final String json = new ResultJson<>(HttpStatus.OK.value(), "OK").getStringResult();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping(path = "/session", method = RequestMethod.DELETE,
                                            produces = "application/json")
    public ResponseEntity signOut(HttpSession httpSession) {
        try {
            sessionService.signOut(httpSession.getId());
            return ResponseEntity.ok("{OK}");
        } catch (AirDroneExeptions.NotLoggedInException e) {
            final String errJson = (new ResultJson<>(HttpStatus.UNAUTHORIZED.value(),
                    new UserNotFoundException().getMessage())).getStringResult();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errJson);
        }
    }

    @ExceptionHandler({CannotCreateTransactionException.class})
    @ResponseBody
    public ResponseEntity resolveIOException() {
        final String errJson = new ResultJson<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "DataBaseUnavailible").getStringResult();
        return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(errJson);
    }

}
