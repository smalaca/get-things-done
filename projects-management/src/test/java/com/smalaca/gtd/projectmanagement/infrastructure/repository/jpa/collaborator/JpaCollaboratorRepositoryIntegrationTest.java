package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.collaborator;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@IntegrationTest
@Import({UserTestRepository.class})
class JpaCollaboratorRepositoryIntegrationTest {
    private final List<UUID> ids = new ArrayList<>();
    @Autowired private UserTestRepository userTestRepository;
    @Autowired private SpringDataJpaUserRepository springDataJpaUserRepository;
    private CollaboratorRepository collaboratorRepository;

    @BeforeEach
    void initRepository() {
        collaboratorRepository = new JpaCollaboratorRepository(springDataJpaUserRepository);
        initCollaborators();
    }

    private void initCollaborators() {
        givenCollaborator();
        givenCollaborator();
        givenCollaborator();
    }

    @AfterEach
    void deleteCreatedIdea() {
        ids.forEach(springDataJpaUserRepository::deleteById);
    }

    @Test
    void shouldRecognizeWhenCollaboratorDoesExists() {
        CollaboratorId id = CollaboratorId.from(UUID.randomUUID());

        boolean actual = collaboratorRepository.existsBy(id);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeWhenCollaboratorExists() {
        CollaboratorId id = givenCollaborator();

        boolean actual = collaboratorRepository.existsBy(id);

        assertThat(actual).isTrue();
    }

    private CollaboratorId givenCollaborator() {
        String name = randomString();
        String password = randomString();
        UUID id = userTestRepository.save(UserTestFactory.user(name, password));

        return CollaboratorId.from(id);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}