package ru.mail.park.model.user;


import ru.mail.park.controllers.api.exeptions.AirDroneExeptions;
import ru.mail.park.util.RequestValidator;

public class UserProfile {

    private String username;
    private String email;
    private String password;

    public UserProfile(String username, String email, String password) throws
            AirDroneExeptions.UserBadEmailException, AirDroneExeptions.UserBadPasswordException {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserProfile() {}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return  email; }

    public void setEmail(String email) {
        this.email = email;
    }
}
