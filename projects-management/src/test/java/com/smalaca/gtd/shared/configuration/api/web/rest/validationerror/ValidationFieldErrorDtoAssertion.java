package com.smalaca.gtd.shared.configuration.api.web.rest.validationerror;

import org.assertj.core.api.Assertions;

public class ValidationFieldErrorDtoAssertion {
    private final ValidationFieldErrorDto actual;

    private ValidationFieldErrorDtoAssertion(ValidationFieldErrorDto actual) {
        this.actual = actual;
    }

    public static ValidationFieldErrorDtoAssertion assertThat(ValidationFieldErrorDto actual) {
        return new ValidationFieldErrorDtoAssertion(actual);
    }

    public ValidationFieldErrorDtoAssertion forField(String expected) {
        return forFields(expected);
    }

    ValidationFieldErrorDtoAssertion forFields(String... expected) {
        Assertions.assertThat(actual.getFields()).containsExactlyInAnyOrder(expected);
        return this;
    }

    public ValidationFieldErrorDtoAssertion hasMessage(String expected) {
        Assertions.assertThat(actual.getMessage()).isEqualTo(expected);
        return this;
    }
}
