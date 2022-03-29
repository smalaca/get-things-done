package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class IdeaFactoryTest {
    private static final UUID OWNER_UUID = UUID.randomUUID();
    private static final AuthorId OWNER_ID = AuthorId.from(OWNER_UUID);
    private final IdeaFactory factory = new IdeaFactory();

    @Test
    void shouldCreateIdeaWithTitle() {
        CreateIdeaCommand command = command("Great idea!!!", null);

        Idea actual = factory.create(command);

        assertThat(actual)
                .hasAuthorId(OWNER_ID)
                .hasTitle("Great idea!!!")
                .hasNoDescription();
    }

    @Test
    void shouldCreateIdeaWithDescription() {
        CreateIdeaCommand command = command(null, "Sometimes you need more space to describe your idea");

        Idea actual = factory.create(command);

        assertThat(actual)
                .hasAuthorId(OWNER_ID)
                .hasNoTitle()
                .hasDescription("Sometimes you need more space to describe your idea");
    }

    @Test
    void shouldCreateIdeaWithTitleAndDescription() {
        CreateIdeaCommand command = command("Got it", "But explanation require even more place");

        Idea actual = factory.create(command);

        assertThat(actual)
                .hasAuthorId(OWNER_ID)
                .hasTitle("Got it")
                .hasDescription("But explanation require even more place");
    }

    @ParameterizedTest
    @MethodSource("noTitleAndDescription")
    void shouldRecognizeBothTitleAndDescriptionAreNotGiven(String title, String description) {
        CreateIdeaCommand command = command(title, description);

        IdeaException actual = assertThrows(IdeaException.class, () -> factory.create(command));

        assertThat(actual).hasMessage("Cannot create idea without title and description");
    }

    public static Stream<Arguments> noTitleAndDescription() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""),
                Arguments.of(null, "  "),
                Arguments.of("", null),
                Arguments.of("  ", null),
                Arguments.of("", ""),
                Arguments.of("  ", "  "),
                Arguments.of("  ", ""),
                Arguments.of("", "  ")
        );
    }

    private CreateIdeaCommand command(String title, String description) {
        return CreateIdeaCommand.create(OWNER_UUID, title, description);
    }
}