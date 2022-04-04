package com.smalaca.gtd.usermanagement.persistence.user;

public class UserTestFactory {
    public User user(String userName, String password) {
        return User.user(userName, password);
    }
}