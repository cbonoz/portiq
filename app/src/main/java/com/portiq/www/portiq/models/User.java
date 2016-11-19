package com.portiq.www.portiq.models;

import java.io.Serializable;

public class User implements Serializable {

    public String user;
    public String email;
    public String position;
    public String company;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(DummyItem.class)
    }

    public User(String username, String email) {
        this.user = username;
        this.email = email;
    }

    public User(String username, String email, String position, String company) {
        this.user = username;
        this.email = email;
        this.position = position;
        this.company = company;
    }

}
// [END blog_user_class]
