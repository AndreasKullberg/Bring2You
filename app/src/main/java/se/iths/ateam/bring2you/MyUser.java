package com.example.ateam.bring2you;

public class MyUser {
    private boolean admin;
    private boolean loggedIn;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public MyUser(){

    }
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
