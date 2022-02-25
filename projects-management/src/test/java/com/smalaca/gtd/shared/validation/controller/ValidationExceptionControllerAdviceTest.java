package com.smalaca.gtd.shared.validation.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

import static com.smalaca.gtd.shared.validation.controller.ValidationFieldErrorDtoAssertion.assertThat;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

class ValidationExceptionControllerAdviceTest {
    private static final String VALIDATED_OBJECT = "name of invalid object";
    private final ValidationExceptionControllerAdvice advice = new ValidationExceptionControllerAdvice();

    @Test
    void shouldCreateResponseWithValidationErrors() {
        MethodArgumentNotValidException exception = givenException(
                givenObjectError(new String[]{"fieldOne", "fieldTwo", "fieldThree"}, "very invalid"),
                givenObjectError(new String[]{"one", "two"}, "can't be good"),
                givenObjectError(new String[]{"invalidField"}, "validation went bad"),
                givenFieldError("field13", "your lucky number did not work this time")
        );

        ResponseEntity<ValidationErrorsDto> actual = advice.handle(exception);

        assertThat(actual.getStatusCode()).isEqualTo(OK);
        assertThat(actual.getBody().getErrors())
                .hasSize(4)
                .anySatisfy(error -> assertThat(error)
                        .forFields("fieldOne", "fieldTwo", "fieldThree")
                        .hasMessage("very invalid"))
                .anySatisfy(error -> assertThat(error)
                        .forFields("one", "two")
                        .hasMessage("can't be good"))
                .anySatisfy(error -> assertThat(error)
                        .forField("invalidField")
                        .hasMessage("validation went bad"))
                .anySatisfy(error -> assertThat(error)
                        .forField("field13")
                        .hasMessage("your lucky number did not work this time"));
    }

    private FieldError givenFieldError(String field, String message) {
        return new FieldError(VALIDATED_OBJECT, field, message);
    }

    private ObjectError givenObjectError(String[] fields, String message) {
        return givenObjectError(new Object[]{null, fields}, message);
    }

    @ParameterizedTest
    @MethodSource("invalidArguments")
    void shouldRecognizeInvalidArguments(ObjectError error, String expectedArguments) {
        MethodArgumentNotValidException exception = givenException(error);

        MissingValidationArgumentException actual = assertThrows(MissingValidationArgumentException.class, () -> advice.handle(exception));

        assertThat(actual)
                .hasMessageContaining("Error in object 'name of invalid object': codes [];")
                .hasMessageContaining(expectedArguments)
                .hasMessageContaining("default message [validation message]");
    }

    public static Stream<Arguments> invalidArguments() {
        return Stream.of(
                Arguments.of(givenObjectError((Object[]) null, "validation message"), "arguments [];"),
                Arguments.of(givenObjectError(new Object[]{}, "validation message"), "arguments [];"),
                Arguments.of(givenObjectError(new Object[]{"irrelevant", "invalid"}, "validation message"), "arguments [irrelevant,invalid];")
        );
    }

    private static ObjectError givenObjectError(Object[] attributes, String message) {
        return new ObjectError(VALIDATED_OBJECT, null, attributes, message);
    }

    private MethodArgumentNotValidException givenException(ObjectError... errors) {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        given(bindingResult.getAllErrors()).willReturn(asList(errors));

        return new MethodArgumentNotValidException(null, bindingResult);
    }
}