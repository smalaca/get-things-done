package com.smalaca.gtd.usermanagement.persistence.user;

import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@IntegrationTest
class UserRepositoryIntegrationTest {
    @Autowired private CrudUserRepository crudUserRepository;
    private UserRepository repository;
    private String id;

    @BeforeEach
    void initRepository() {
        repository =  new UserRepository(crudUserRepository);
    }

    @AfterEach
    void removeUser() {
        if (id != null) {
            crudUserRepository.deleteById(UUID.fromString(id));
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
        return crudUserRepository.findById(UUID.fromString(id)).get();
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