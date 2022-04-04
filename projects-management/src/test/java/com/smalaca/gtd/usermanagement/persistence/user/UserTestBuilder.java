package com.smalaca.gtd.usermanagement.persistence.user;

public class UserTestBuilder {
    private final String userName;
    private String password;

    private UserTestBuilder(String userName) {
        this.userName = userName;
    }

    public static UserTestBuilder user(String userName) {
        return new UserTestBuilder(userName);
    }

    public UserTestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return User.user(userName, password);
    }
}
