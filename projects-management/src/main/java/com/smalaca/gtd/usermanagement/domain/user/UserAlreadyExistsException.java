package com.smalaca.gtd.usermanagement.domain.user;

public class UserAlreadyExistsException extends RuntimeException {
    UserAlreadyExistsException(String userName) {
        super("User \"" + userName + "\" already exists.");
    }
}
