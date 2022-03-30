package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorException;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdeaTest {
    private final IdeaTestFactory ideaFactory = new IdeaTestFactory();
    private final CollaboratorRepository collaboratorRepository = mock(CollaboratorRepository.class);

    @Test
    void shouldRecognizeCollaboratorDoesNotExist() {
        Idea idea = ideaFactory.random();
        CollaboratorId collaboratorId = givenNotExistingCollaborator();

        ThrowingRunnable executable = () -> idea.share(collaboratorRepository, collaboratorId);

        CollaboratorException actual = assertThrows(CollaboratorException.class, executable);
        assertThat(actual).hasMessage("Collaborator: " + collaboratorId + " does not exist.");
    }

    private CollaboratorId givenNotExistingCollaborator() {
        CollaboratorId collaboratorId = CollaboratorId.from(UUID.randomUUID());
        given(collaboratorRepository.existsBy(collaboratorId)).willReturn(false);
        return collaboratorId;
    }

    @Test
    void shouldShareIdea() {
        Idea idea = ideaFactory.random();
        CollaboratorId collaboratorId = givenExistingCollaborator();

        idea.share(collaboratorRepository, collaboratorId);

        assertThat(idea).hasCollaborators(collaboratorId);
    }

    @Test
    void shouldShareIdeaWithManyCollaborators() {
        Idea idea = ideaFactory.random();
        CollaboratorId collaboratorIdOne = givenExistingCollaborator();
        CollaboratorId collaboratorIdTwo = givenExistingCollaborator();
        CollaboratorId collaboratorIdThree = givenExistingCollaborator();

        idea.share(collaboratorRepository, collaboratorIdOne);
        idea.share(collaboratorRepository, collaboratorIdTwo);
        idea.share(collaboratorRepository, collaboratorIdThree);

        assertThat(idea).hasCollaborators(collaboratorIdOne, collaboratorIdTwo, collaboratorIdThree);
    }

    @Test
    void shouldIgnoreSharingIdeaWithTheSameCollaboratorMoreThanOnce() {
        Idea idea = ideaFactory.random();
        CollaboratorId collaboratorId = givenExistingCollaborator();

        idea.share(collaboratorRepository, collaboratorId);
        idea.share(collaboratorRepository, collaboratorId);
        idea.share(collaboratorRepository, collaboratorId);

        assertThat(idea).hasCollaborators(collaboratorId);
    }

    private CollaboratorId givenExistingCollaborator() {
        CollaboratorId collaboratorId = CollaboratorId.from(UUID.randomUUID());
        given(collaboratorRepository.existsBy(collaboratorId)).willReturn(true);
        return collaboratorId;
    }
}