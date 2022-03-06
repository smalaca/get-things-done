package com.smalaca.gtd.usermanagement.domain.user;

import com.smalaca.gtd.usermanagement.persistence.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserServiceFactory {
    @Bean
    UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserService(new UserFactory(userRepository, passwordEncoder), userRepository);
    }
}
