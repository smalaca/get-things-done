package com.smalaca.gtd.usermanagement.domain.user;

public class UserAlreadyExistsExceptionTestFactory {
    public static UserAlreadyExistsException create(String userName) {
        return new UserAlreadyExistsException(userName);
    }
}