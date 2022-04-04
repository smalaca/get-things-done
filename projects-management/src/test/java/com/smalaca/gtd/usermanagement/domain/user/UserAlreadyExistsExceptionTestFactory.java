package com.smalaca.gtd.usermanagement.domain.user;

public class UserAlreadyExistsExceptionTestFactory {
    public UserAlreadyExistsException create(String userName) {
        return new UserAlreadyExistsException(userName);
    }
}