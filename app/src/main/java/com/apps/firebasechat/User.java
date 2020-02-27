package com.apps.firebasechat;

import com.google.firebase.auth.FirebaseAuth;

public class User {
    private String name;
    private String email;
    private String uid;
    private String key;

    public User() {
    }

    public User(String name, String email, String uid, String key) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

}
