package ru.mail.park.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.controllers.api.SignInRequest;
import ru.mail.park.controllers.api.common.ResultJson;
import ru.mail.park.service.interfaces.AbstractSessionService;

import javax.servlet.http.HttpSession;


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

        if(!sessionService.signIn(httpSession.getId(), body.getEmail(), body.getPassword())) {
            final String errJson = (new ResultJson<>(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST)).getStringResult();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errJson);
        }

        final String json = new ResultJson<>(HttpStatus.OK.value(), HttpStatus.OK).getStringResult();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping(path = "/session", method = RequestMethod.DELETE,
                                            produces = "application/json")
    public ResponseEntity signOut(HttpSession httpSession) {

        if(!sessionService.signOut(httpSession.getId())) {
            final String errJson = (new ResultJson<>(HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED)).getStringResult();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errJson);
        }

        final String json = new ResultJson<>(HttpStatus.OK.value(), HttpStatus.OK).getStringResult();
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @ExceptionHandler({CannotCreateTransactionException.class})
    @ResponseBody
    public ResponseEntity resolveIOException() {
        final String errJson = new ResultJson<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "DataBaseUnavailible").getStringResult();
        return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(errJson);
    }

}
