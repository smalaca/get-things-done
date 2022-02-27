package com.smalaca.gtd.usermanagement.domain.user;

import static org.junit.jupiter.api.Assertions.*;

public class UserAlreadyExistsExceptionTestFactory {
    public static UserAlreadyExistsException create(String userName) {
        return new UserAlreadyExistsException(userName);
    }
}