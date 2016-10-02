package ru.mail.park.controllers.entities;

/**
 * Created by admin on 02.10.16.
 */
public final class SessionReqResp {

    private SessionReqResp() {
    }

    public static final class SignInRequest {
        private String email;
        private String password;

        private SignInRequest() {}

        private SignInRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {return email;}
        public String getPassword() {return password;}

    }

    public static  final class AutorizedSessionResponse {
        private final String sessionId;

        public AutorizedSessionResponse(String sessionId) {this.sessionId = sessionId;}

        public String getSessionId() {return  sessionId;}
    }
}
