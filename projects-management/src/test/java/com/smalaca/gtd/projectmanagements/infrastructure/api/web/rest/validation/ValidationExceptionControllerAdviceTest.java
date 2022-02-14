package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.validation;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

class ValidationExceptionControllerAdviceTest {
    private final ValidationExceptionControllerAdvice advice = new ValidationExceptionControllerAdvice();

    @Test
    void shouldCreateResponseWithValidationErrors() {
        MethodArgumentNotValidException exception = givenException(
                givenObjectError(new String[]{"fieldOne", "fieldTwo", "fieldThree"}, "very invalid"),
                givenObjectError(new String[]{"one", "two"}, "can't be good"),
                givenObjectError(new String[]{"invalidField"}, "validation went bad")
        );

        ResponseEntity<ValidationErrorsDto> actual = advice.handle(exception);

        assertThat(actual.getStatusCode()).isEqualTo(OK);
        assertThat(actual.getBody().getErrors())
                .hasSize(3)
                .anySatisfy(error -> {
                    assertThat(error.getFields()).containsExactlyInAnyOrder("fieldOne", "fieldTwo", "fieldThree");
                    assertThat(error.getMessage()).isEqualTo("very invalid");
                })
                .anySatisfy(error -> {
                    assertThat(error.getFields()).containsExactlyInAnyOrder("one", "two");
                    assertThat(error.getMessage()).isEqualTo("can't be good");
                })
                .anySatisfy(error -> {
                    assertThat(error.getFields()).containsExactlyInAnyOrder("invalidField");
                    assertThat(error.getMessage()).isEqualTo("validation went bad");
                });
    }

    private MethodArgumentNotValidException givenException(ObjectError... errors) {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        given(bindingResult.getGlobalErrors()).willReturn(asList(errors));

        return new MethodArgumentNotValidException(null, bindingResult);
    }

    private ObjectError givenObjectError(String[] fields, String message) {
        return new ObjectError("name of invalid object", null, new Object[]{null, fields}, message);
    }
}