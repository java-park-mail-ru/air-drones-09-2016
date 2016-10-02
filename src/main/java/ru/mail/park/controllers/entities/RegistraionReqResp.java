package ru.mail.park.controllers.entities;

/**
 * Created by admin on 02.10.16.
 */
public final class RegistraionReqResp {

    private RegistraionReqResp() {
    }

    public static final class RegistrationRequest {
        private String username;
        private String email;
        private String password;

        private RegistrationRequest() {}

        private RegistrationRequest(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }
    }

    public static  final class AutorizedSessionResponce {
        private final String sessionId;

        public AutorizedSessionResponce(String sessionId) {this.sessionId = sessionId;}

        public String getSessionId() {return  sessionId;}
    }

    public static final class DeleteRequest {
        private String email;
        private String password;

        private DeleteRequest() {}

        private DeleteRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {return email;}

        public String getPassword() {return password;}

    }

}
