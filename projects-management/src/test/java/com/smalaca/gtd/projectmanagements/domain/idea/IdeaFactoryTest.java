package com.smalaca.gtd.projectmanagements.domain.idea;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.smalaca.gtd.projectmanagements.domain.idea.IdeaAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class IdeaFactoryTest {
    private final IdeaFactory factory = new IdeaFactory();

    @Test
    void shouldCreateIdeaWithTitle() {
        CreateIdeaCommand command = new CreateIdeaCommand("Great idea!!!", null);

        Idea actual = factory.create(command);

        assertThat(actual)
                .hasTitle("Great idea!!!")
                .hasNoDescription();
    }

    @Test
    void shouldCreateIdeaWithDescription() {
        CreateIdeaCommand command = new CreateIdeaCommand(null, "Sometimes you need more space to describe your idea");

        Idea actual = factory.create(command);

        assertThat(actual)
                .hasNoTitle()
                .hasDescription("Sometimes you need more space to describe your idea");
    }

    @Test
    void shouldCreateIdeaWithTitleAndDescription() {
        CreateIdeaCommand command = new CreateIdeaCommand("Got it", "But explanation require even more place");

        Idea actual = factory.create(command);

        assertThat(actual)
                .hasTitle("Got it")
                .hasDescription("But explanation require even more place");
    }

    @ParameterizedTest
    @MethodSource("noTitleAndDescription")
    void shouldRecognizeBothTitleAndDescriptionAreNotGiven(String title, String description) {
        CreateIdeaCommand command = new CreateIdeaCommand(title, description);

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
}