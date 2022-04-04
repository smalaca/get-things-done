package com.smalaca.gtd.usermanagement.persistence.user;

import com.smalaca.gtd.tests.annotation.RepositoryTest;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUserManagementTestConfiguration;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import({UserRepository.class, GivenUserManagementTestConfiguration.class})
class UserRepositoryIntegrationTest {
    @Autowired private GivenUsers givenUsers;
    @Autowired private UserTestRepository userTestRepository;
    @Autowired private UserRepository repository;

    @AfterEach
    void removeUser() {
        givenUsers.deleteAll();
    }

    @Test
    void shouldSaveUser() {
        UUID id = givenUsers.existing("captain-america", "5H13LD");

        UserAssertion.assertThat(findBy(id))
                .hasUserName("captain-america")
                .hasPassword("5H13LD")
                .isActive()
                .hasUserRole();
    }

    private User findBy(UUID id) {
        return userTestRepository.findBy(id);
    }

    @Test
    void shouldRecognizeUserWithGivenUserNameDoNotExist() {
        givenUsers.existing("captain-america", "5H13LD");

        boolean actual = repository.exists("charles xavier");

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeUserWithGivenUserNameExists() {
        givenUsers.existing("captain-america", "5H13LD");

        boolean actual = repository.exists("captain-america");

        assertThat(actual).isTrue();
    }
}