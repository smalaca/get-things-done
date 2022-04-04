package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.collaborator;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenProjectManagementTestConfiguration;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenCollaborators;
import com.smalaca.gtd.tests.annotation.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import({JpaCollaboratorRepository.class, GivenProjectManagementTestConfiguration.class})
class JpaCollaboratorRepositoryIntegrationTest {
    @Autowired private GivenCollaborators givenCollaborators;
    @Autowired private CollaboratorRepository collaboratorRepository;

    @BeforeEach
    void initCollaborators() {
        givenCollaborators.existing();
        givenCollaborators.existing();
        givenCollaborators.existing();
    }

    @AfterEach
    void deleteCreatedCollaborators() {
        givenCollaborators.deleteAll();
    }

    @Test
    void shouldRecognizeWhenCollaboratorDoesExists() {
        CollaboratorId id = CollaboratorId.from(UUID.randomUUID());

        boolean actual = collaboratorRepository.existsBy(id);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeWhenCollaboratorExists() {
        CollaboratorId id = givenCollaborators.existing();

        boolean actual = collaboratorRepository.existsBy(id);

        assertThat(actual).isTrue();
    }
}