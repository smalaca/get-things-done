package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
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

    private final IdeaRepository repository = mock(IdeaRepository.class);
    private final IdeaApplicationService service = new IdeaApplicationServiceFactory().ideaApplicationService(repository);

    @BeforeEach
    void givenIdOnIdeaSave() {
        given(repository.save(any())).willReturn(ideaIdFrom(ID));
    }

    @Test
    void shouldReturnIdeaId() {
        CreateIdeaCommand command = new CreateIdeaCommand(TITLE, DESCRIPTION);

        UUID actual = service.create(command);

        assertThat(actual).isEqualTo(ID);
    }

    @Test
    void shouldCreateIdea() {
        CreateIdeaCommand command = new CreateIdeaCommand(TITLE, DESCRIPTION);

        service.create(command);

        assertThat(savedIdea())
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
        CreateIdeaCommand command = new CreateIdeaCommand(null, null);

        assertThrows(RuntimeException.class, () -> service.create(command));

        then(repository).should(never()).save(any());
    }
}
