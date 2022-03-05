package com.smalaca.gtd.usermanagement.domain.user;

public class UserTestFactory {
    public static User user(String userName, String password) {
        return User.user(userName, password);
    }
}