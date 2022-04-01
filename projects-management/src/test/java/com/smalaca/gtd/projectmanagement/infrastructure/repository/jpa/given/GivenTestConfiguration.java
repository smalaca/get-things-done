package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.given;

import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@TestConfiguration
@Import(UserTestRepository.class)
public class GivenTestConfiguration {
    private final UserTestRepository userTestRepository;

    GivenTestConfiguration(UserTestRepository userTestRepository) {
        this.userTestRepository = userTestRepository;
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public GivenCollaborators givenCollaborators() {
        return new GivenCollaborators(userTestRepository);
    }
}
