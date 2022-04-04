package com.smalaca.gtd.usermanagement.persistence.given;

import com.smalaca.gtd.usermanagement.persistence.user.UserRepository;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@TestConfiguration
@Import({UserRepository.class, UserTestRepository.class})
public class GivenUserManagementTestConfiguration {

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public GivenUsers givenUsers(UserRepository userRepository, UserTestRepository userTestRepository) {
        return new GivenUsers(userRepository, userTestRepository, new UserTestFactory());
    }
}
