package ru.mail.park.model;


import java.util.concurrent.atomic.AtomicLong;

public class UserProfile {

    private static final AtomicLong ID_GENETATOR = new AtomicLong(1);

    private final long id;
    private String username;
    private final String email;
    private String password;


    public UserProfile(String username, String email, String password) {
        this.id = ID_GENETATOR.getAndIncrement();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return  email; }

}
