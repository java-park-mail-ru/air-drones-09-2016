package ru.mail.park.model;


import java.util.concurrent.atomic.AtomicLong;

public class UserProfile {

    private static final AtomicLong ID_GENETATOR = new AtomicLong(0);

    private final long id;
    private String username;
    private final String email;
    private final String password;


    public UserProfile(String username, String email, String password) {
        this.id = ID_GENETATOR.getAndAdd(1);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public String getEmail() { return  email; }

}
