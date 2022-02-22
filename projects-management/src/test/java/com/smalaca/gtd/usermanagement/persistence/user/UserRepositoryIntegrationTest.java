package com.smalaca.gtd.usermanagement.persistence.user;

import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.smalaca.gtd.usermanagement.persistence.user.UserAssertion.assertThat;

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
        id = repository.save(User.user("captain-america", "5H13LD"));

        assertThat(findBy(id))
                .hasUserName("captain-america")
                .hasPassword("5H13LD")
                .isActive()
                .hasUserRole();
    }

    private User findBy(String id) {
        return crudUserRepository.findById(UUID.fromString(id)).get();
    }
}