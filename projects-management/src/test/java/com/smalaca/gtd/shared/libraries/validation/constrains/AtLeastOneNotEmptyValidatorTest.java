package com.smalaca.gtd.shared.libraries.validation.constrains;

import lombok.Builder;
import lombok.Getter;
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

class AtLeastOneNotEmptyValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("invalidSomeDto")
    void shouldRecognizeInvalidSomeDto(String fieldOne, String fieldTwo, String fieldThree) {
        SomeDto dto = SomeDto.builder()
                .fieldOne(fieldOne)
                .fieldTwo(fieldTwo)
                .fieldThree(fieldThree)
                .fieldFour("Just after this one")
                .build();

        Set<ConstraintViolation<SomeDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(1)
                .allSatisfy(violation -> {
                    assertThat(violation.getMessage()).isEqualTo("fieldOne, fieldTwo or fieldThree cannot be empty.");
                });
    }

    public static Stream<Arguments> invalidSomeDto() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, null, ""),
                Arguments.of(null, null, "  "),
                Arguments.of(null, "", null),
                Arguments.of(null, "", ""),
                Arguments.of(null, "", "  "),
                Arguments.of(null, "  ", null),
                Arguments.of(null, "  ", ""),
                Arguments.of(null, "  ", "  "),
                Arguments.of("", null, null),
                Arguments.of("", null, ""),
                Arguments.of("", null, "  "),
                Arguments.of("", "", null),
                Arguments.of("", "", ""),
                Arguments.of("", "", "  "),
                Arguments.of("", "  ", null),
                Arguments.of("", "  ", ""),
                Arguments.of("", "  ", "  "),
                Arguments.of("  ", null, null),
                Arguments.of("  ", null, ""),
                Arguments.of("  ", null, "  "),
                Arguments.of("  ", "", null),
                Arguments.of("  ", "", ""),
                Arguments.of("  ", "", "  "),
                Arguments.of("  ", "  ", null),
                Arguments.of("  ", "  ", ""),
                Arguments.of("  ", "  ", "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("validSomeDto")
    void shouldRecognizeValidSomeDto(String fieldOne, String fieldTwo, String fieldThree) {
        SomeDto dto = SomeDto.builder()
                .fieldOne(fieldOne)
                .fieldTwo(fieldTwo)
                .fieldThree(fieldThree)
                .build();

        Set<ConstraintViolation<SomeDto>> actual = validator.validate(dto);

        assertThat(actual).isEmpty();
    }

    public static Stream<Arguments> validSomeDto() {
        return Stream.of(
                Arguments.of("I've", "got", "a dream"),
                Arguments.of(null, null, "I've got a dream"),
                Arguments.of("", "", "I've got a dream"),
                Arguments.of("  ", "  ", "I've got a dream"),
                Arguments.of("I've got a dream", null, null),
                Arguments.of("I've got a dream", "", ""),
                Arguments.of("I've got a dream", "  ", "  "),
                Arguments.of(null, "I've got a dream", null),
                Arguments.of("", "I've got a dream", ""),
                Arguments.of("  ", "I've got a dream", "  ")
        );
    }

    @Test
    void shouldReturnDefaultMessageWhenInvalid() {
        AnotherDto dto = AnotherDto.builder().build();

        Set<ConstraintViolation<AnotherDto>> actual = validator.validate(dto);

        assertThat(actual).allSatisfy(violation -> {
            assertThat(violation.getMessage()).isEqualTo("At least one of the fields must be set");
        });
    }

    @Builder
    @Getter
    @AtLeastOneNotEmpty(
            fields = {"fieldOne", "fieldTwo", "fieldThree"},
            message = "fieldOne, fieldTwo or fieldThree cannot be empty."
    )
    static class SomeDto {
        private final String fieldOne;
        private final String fieldTwo;
        private final String fieldThree;
        private final String fieldFour;
    }

    @Builder
    @Getter
    @AtLeastOneNotEmpty(
            fields = {"fieldOne", "fieldTwo", "fieldThree"}
    )
    static class AnotherDto {
        private final String fieldOne;
        private final String fieldTwo;
        private final String fieldThree;
        private final String fieldFour;
    }
}