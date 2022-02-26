package com.smalaca.gtd.usermanagement.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String userName, String password) {
        return User.user(userName, passwordEncoder.encode(password));
    }
}
