package com.smalaca.gtd.usermanagement.domain.user;

import com.smalaca.gtd.usermanagement.persistence.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserFactory {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    UserFactory(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    User create(String userName, String password) {
        if (repository.exists(userName)) {
            throw new UserAlreadyExistsException(userName);
        }

        return User.user(userName, passwordEncoder.encode(password));
    }
}
