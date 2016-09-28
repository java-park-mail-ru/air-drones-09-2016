package ru.mail.park.model;


import java.util.concurrent.atomic.AtomicLong;

public class UserProfile {

    private static final AtomicLong ID_GENETATOR = new AtomicLong(0);

    private final long id;
    private String login;
    private String email;
    private String password;


    public UserProfile(String login, String email, String password) {
        this.id = ID_GENETATOR.getAndAdd(1);
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() { return  email; }

    public long getId() { return id; }
}
