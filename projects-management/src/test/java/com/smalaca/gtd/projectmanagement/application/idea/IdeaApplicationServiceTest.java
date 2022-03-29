package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
    private static final UUID OWNER_UUID = UUID.randomUUID();
    private static final AuthorId OWNER_ID = AuthorId.from(OWNER_UUID);

    private final IdeaRepository repository = mock(IdeaRepository.class);
    private final IdeaApplicationService service = new IdeaApplicationServiceFactory().ideaApplicationService(repository);

    @BeforeEach
    void givenIdOnIdeaSave() {
        given(repository.save(any())).willReturn(ideaIdFrom(ID));
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
                .hasAuthorId(OWNER_ID)
                .hasTitle(TITLE)
                .hasDescription(DESCRIPTION);
    }

    private Idea savedIdea() {
        ArgumentCaptor<Idea> captor = ArgumentCaptor.forClass(Idea.class);
        then(repository).should().save(captor.capture());

        return captor.getValue();
    }

    @Test
    void shouldNotSaveIdeaInCaseOfInvalidData() {
        CreateIdeaCommand command = command(null, null);

        assertThrows(RuntimeException.class, () -> service.create(command));

        then(repository).should(never()).save(any());
    }

    private CreateIdeaCommand command(String title, String description) {
        return CreateIdeaCommand.create(OWNER_UUID, title, description);
    }
}
