package com.smalaca.gtd.usermanagement.persistence.user;

import com.smalaca.gtd.tests.annotation.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import({UserRepository.class, UserTestRepository.class})
class UserRepositoryIntegrationTest {
    private String id;

    @Autowired private UserTestRepository userTestRepository;
    @Autowired private UserRepository repository;

    @AfterEach
    void removeUser() {
        if (id != null) {
            userTestRepository.deleteBy(UUID.fromString(id));
        }
    }

    @Test
    void shouldSaveUser() {
        id = repository.save(UserTestFactory.user("captain-america", "5H13LD"));

        UserAssertion.assertThat(findBy(id))
                .hasUserName("captain-america")
                .hasPassword("5H13LD")
                .isActive()
                .hasUserRole();
    }

    private User findBy(String id) {
        return userTestRepository.findBy(UUID.fromString(id));
    }

    @Test
    void shouldRecognizeUserWithGivenUserNameDoNotExist() {
        repository.save(UserTestFactory.user("captain-america", "5H13LD"));

        boolean actual = repository.exists("charles xavier");

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeUserWithGivenUserNameExists() {
        repository.save(UserTestFactory.user("captain-america", "5H13LD"));

        boolean actual = repository.exists("captain-america");

        assertThat(actual).isTrue();
    }
}