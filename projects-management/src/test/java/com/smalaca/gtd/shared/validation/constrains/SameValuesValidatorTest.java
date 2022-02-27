package com.smalaca.gtd.shared.validation.constrains;

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

class SameValuesValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("invalidSomeDto")
    void shouldRecognizeInvalidSomeDto(String fieldOne, String fieldTwo) {
        SomeDto dto = SomeDto.builder()
                .fieldOne(fieldOne)
                .fieldTwo(fieldTwo)
                .build();

        Set<ConstraintViolation<SomeDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(1)
                .allSatisfy(violation -> {
                    assertThat(violation.getMessage()).isEqualTo("Passwords are different.");
                });
    }

    public static Stream<Arguments> invalidSomeDto() {
        return Stream.of(
                Arguments.of(null, "valueTwo"),
                Arguments.of("", "valueTwo"),
                Arguments.of("valueOne", null),
                Arguments.of("valueOne", ""),
                Arguments.of("valueOne", "valueTwo"),
                Arguments.of("valueOne", "VALUEONE"),
                Arguments.of("valueOne", "valueone")
        );
    }

    @Test
    void shouldRecognizeValidSomeDto() {
        SomeDto dto = SomeDto.builder()
                .fieldOne("value one")
                .fieldTwo("value one")
                .build();

        Set<ConstraintViolation<SomeDto>> actual = validator.validate(dto);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnDefaultMessageWhenInvalid() {
        AnotherDto dto = AnotherDto.builder()
                .fieldOne("valueOne")
                .fieldTwo("valueTwo")
                .build();

        Set<ConstraintViolation<AnotherDto>> actual = validator.validate(dto);

        assertThat(actual).allSatisfy(violation -> {
            assertThat(violation.getMessage()).isEqualTo("Values have to be the same.");
        });
    }

    @Builder
    @Getter
    @SameValues(
            fieldOne = "fieldOne",
            fieldTwo = "fieldTwo",
            message = "Passwords are different."
    )
    static class SomeDto {
        private final String fieldOne;
        private final String fieldTwo;
    }

    @Builder
    @Getter
    @SameValues(
            fieldOne = "fieldOne",
            fieldTwo = "fieldTwo"
    )
    static class AnotherDto {
        private final String fieldOne;
        private final String fieldTwo;
    }
}