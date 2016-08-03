package com.gmail.austintingwork.googlefirebase;

/**
 * Created by cellbody on 2016/8/2.
 */
public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
