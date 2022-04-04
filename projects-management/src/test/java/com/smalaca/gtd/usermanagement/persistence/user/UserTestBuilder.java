package com.smalaca.gtd.usermanagement.persistence.user;

import java.util.UUID;

public class UserTestBuilder {
    private final String userName;
    private String password = UUID.randomUUID().toString();

    private UserTestBuilder(String userName) {
        this.userName = userName;
    }

    public static UserTestBuilder user(String userName) {
        return new UserTestBuilder(userName);
    }

    public static UserTestBuilder randomUser() {
        return new UserTestBuilder(UUID.randomUUID().toString());
    }

    public UserTestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return User.user(userName, password);
    }
}
