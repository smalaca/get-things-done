package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class IdeaCreateDtoTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static Stream<Arguments> invalidIdeaDto() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""),
                Arguments.of(null, "  "),
                Arguments.of("", null),
                Arguments.of("", ""),
                Arguments.of("", "  "),
                Arguments.of("  ", null),
                Arguments.of("  ", ""),
                Arguments.of("  ", "  ")
        );
    }

    public static Stream<Arguments> validIdeaDto() {
        return Stream.of(
                Arguments.of("I've got", "a dream"),
                Arguments.of(null, "I've got a dream"),
                Arguments.of("", "I've got a dream"),
                Arguments.of("  ", "I've got a dream"),
                Arguments.of("I've got a dream", null),
                Arguments.of("I've got a dream", ""),
                Arguments.of("I've got a dream", "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidIdeaDto")
    void shouldRecognizeInvalidIdeaDto(String title, String description) {
        IdeaCreateDto dto = new IdeaCreateDto(title, description);

        Set<ConstraintViolation<IdeaCreateDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(1)
                .allSatisfy(violation -> {
                    assertThat(violation.getMessage()).isEqualTo("Title or description cannot be empty.");
                });
    }

    @ParameterizedTest
    @MethodSource("validIdeaDto")
    void shouldRecognizeValidIdeaDto(String title, String description) {
        IdeaCreateDto dto = new IdeaCreateDto(title, description);

        Set<ConstraintViolation<IdeaCreateDto>> actual = validator.validate(dto);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(IdeaCreateDto.class).verify();
    }

}