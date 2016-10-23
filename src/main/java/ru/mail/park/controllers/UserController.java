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
import ru.mail.park.controllers.api.common.ResultJson;
import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;
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
        String username = body.getUsername();

        try {
            UserProfile userProfile = accountService.addUser(username,
                    body.getEmail(), body.getPassword());
            sessionService.addAuthorizedLogin(sessionId, body.getEmail());
            userProfile.setPassword(null);
            return ResponseEntity.status(HttpStatus.OK).body((new ResultJson<UserProfile>(
                    HttpStatus.OK.value(), userProfile)).getStringResult());
        } catch (org.jooq.exception.DataAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    (new ResultJson<String>(
                        HttpStatus.FORBIDDEN.value(),
                        new AirDroneExeptions.UserExistEmailException().getMessage())).getStringResult());
        }

//        return ResponseEntity.ok("{OK}");
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET,
                                            produces = "application/json")
    public ResponseEntity getUser(HttpSession httpSession) {

        final String sessionId = httpSession.getId();

        final String email = sessionService.getAuthorizedEmail(sessionId);

//        if(email == null)
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

        final UserProfile up =  accountService.getUser(email);

        return ResponseEntity.ok( new GetUserResponce(
                up.getEmail(), up.getUsername()));
    }

    @RequestMapping(path = "/user", method = RequestMethod.DELETE,
                                                produces = "application/json")
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest body,
                                     HttpSession httpSession) {

        if(sessionService.getAuthorizedEmail(httpSession.getId()) == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

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

    @RequestMapping(path = "/user", method = RequestMethod.PUT,
                                        produces = "application/json")
    public ResponseEntity putUser(@RequestBody PutUserRequest body,
                                     HttpSession httpSession) {

        if(sessionService.getAuthorizedEmail(httpSession.getId()) == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");

        accountService.updateUser(body.getUsername(), body.getEmail(),
                                    body.getPassword(), body.getNewPassword());

////        if (StringUtils.isEmpty(body.getEmail()) || StringUtils.isEmpty(body.getPassword())) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
////        }
//
//        final UserProfile userProfile = accountService.getUser(body.getEmail());
//
//        if(userProfile == null || !userProfile.getEmail().equals(body.getEmail()) ||
//                !userProfile.getPassword().equals(body.getPassword()))
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{}");
//
//        if(!StringUtils.isEmpty(body.getUsername()))
//            userProfile.setUsername(body.getUsername());
//
//        final String newPassword = body.getNewPassword();
//        final String oldPassword = body.getPassword();
//
//        if(!StringUtils.isEmpty(newPassword)) {
//            if (!oldPassword.equals(newPassword) &&
//                    RequestValidator.passwordValidate(newPassword)) {
//                userProfile.setPassword(newPassword);
//            }
//        }
//            else ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");

        return ResponseEntity.ok("{OK}");
    }

}
