package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;
import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaIdTestFactory.ideaIdFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class IdeaApplicationServiceTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String TITLE = "Got it";
    private static final String DESCRIPTION = "But explanation require even more place";
    private static final UUID AUTHOR_UUID = UUID.randomUUID();
    private static final AuthorId AUTHOR_ID = AuthorId.from(AUTHOR_UUID);

    private final IdeaTestFactory factory = new IdeaTestFactory();

    private final IdeaRepository ideaRepository = mock(IdeaRepository.class);
    private final CollaboratorRepository collaboratorRepository = mock(CollaboratorRepository.class);
    private final IdeaApplicationService service = new IdeaApplicationServiceFactory().ideaApplicationService(ideaRepository, collaboratorRepository);

    @BeforeEach
    void givenIdOnIdeaSave() {
        given(ideaRepository.save(any())).willReturn(ideaIdFrom(ID));
    }

    @Test
    void shouldReturnIdeaId() {
        CreateIdeaCommand command = command(TITLE, DESCRIPTION);

        UUID actual = service.create(command);

        assertThat(actual).isEqualTo(ID);
    }

    @Test
    void shouldCreateIdea() {
        CreateIdeaCommand command = command(TITLE, DESCRIPTION);

        service.create(command);

        assertThat(savedIdea())
                .hasAuthorId(AUTHOR_ID)
                .hasTitle(TITLE)
                .hasDescription(DESCRIPTION);
    }

    @Test
    void shouldNotSaveIdeaInCaseOfInvalidData() {
        CreateIdeaCommand command = command(null, null);

        assertThrows(RuntimeException.class, () -> service.create(command));

        thenIdeaWasNotSaved();
    }

    private CreateIdeaCommand command(String title, String description) {
        return CreateIdeaCommand.create(AUTHOR_UUID, title, description);
    }

    @Test
    void shouldRecognizeCollaboratorDoesNotExist() {
        UUID ideaId = givenExistingIdea();
        UUID collaboratorId = givenNotExistingCollaborator();

        assertThrows(RuntimeException.class, () -> service.share(shareIdeaCommand(ideaId, collaboratorId)));

        thenIdeaWasNotSaved();
    }

    private UUID givenNotExistingCollaborator() {
        UUID id = UUID.randomUUID();
        given(collaboratorRepository.existsBy(CollaboratorId.from(id))).willReturn(false);
        return id;
    }

    @Test
    void shouldShareIdea() {
        UUID ideaId = givenExistingIdea();
        UUID collaboratorId = givenExistingCollaborator();

        service.share(shareIdeaCommand(ideaId, collaboratorId));

        assertThat(savedIdea()).hasCollaborators(collaboratorId);
    }

    @Test
    void shouldShareIdeaWithManyCollaborators() {
        UUID ideaId = givenExistingIdea();
        UUID collaboratorIdOne = givenExistingCollaborator();
        UUID collaboratorIdTwo = givenExistingCollaborator();
        UUID collaboratorIdThree = givenExistingCollaborator();

        service.share(shareIdeaCommand(ideaId, collaboratorIdOne));
        service.share(shareIdeaCommand(ideaId, collaboratorIdTwo));
        service.share(shareIdeaCommand(ideaId, collaboratorIdThree));

        assertThat(savedIdea(3)).hasCollaborators(collaboratorIdOne, collaboratorIdTwo, collaboratorIdThree);
    }

    @Test
    void shouldIgnoreSharingIdeaWithTheSameCollaboratorMoreThanOnce() {
        UUID ideaId = givenExistingIdea();
        UUID collaboratorId = givenExistingCollaborator();

        service.share(shareIdeaCommand(ideaId, collaboratorId));
        service.share(shareIdeaCommand(ideaId, collaboratorId));
        service.share(shareIdeaCommand(ideaId, collaboratorId));

        assertThat(savedIdea(3)).hasCollaborators(collaboratorId);
    }

    private ShareIdeaCommand shareIdeaCommand(UUID ideaId, UUID collaboratorId) {
        return new ShareIdeaCommand(AUTHOR_UUID, ideaId, collaboratorId);
    }

    private UUID givenExistingIdea() {
        UUID id = UUID.randomUUID();
        given(ideaRepository.findBy(AUTHOR_ID, ideaIdFrom(id))).willReturn(factory.create(AUTHOR_UUID, TITLE, DESCRIPTION));
        return id;
    }

    private UUID givenExistingCollaborator() {
        UUID id = UUID.randomUUID();
        given(collaboratorRepository.existsBy(CollaboratorId.from(id))).willReturn(true);
        return id;
    }

    private void thenIdeaWasNotSaved() {
        then(ideaRepository).should(never()).save(any());
    }

    private Idea savedIdea() {
        return savedIdea(1);
    }

    private Idea savedIdea(int times) {
        ArgumentCaptor<Idea> captor = ArgumentCaptor.forClass(Idea.class);
        then(ideaRepository).should(Mockito.times(times)).save(captor.capture());

        return captor.getValue();
    }
}
