package com.smalaca.gtd.shared.validation.controller;

import org.assertj.core.api.Assertions;

class ValidationFieldErrorDtoAssertion {
    private final ValidationFieldErrorDto actual;

    private ValidationFieldErrorDtoAssertion(ValidationFieldErrorDto actual) {
        this.actual = actual;
    }

    static ValidationFieldErrorDtoAssertion assertThat(ValidationFieldErrorDto actual) {
        return new ValidationFieldErrorDtoAssertion(actual);
    }

    ValidationFieldErrorDtoAssertion forField(String expected) {
        return forFields(expected);
    }

    ValidationFieldErrorDtoAssertion forFields(String... expected) {
        Assertions.assertThat(actual.getFields()).containsExactlyInAnyOrder(expected);
        return this;
    }

    ValidationFieldErrorDtoAssertion hasMessage(String expected) {
        Assertions.assertThat(actual.getMessage()).isEqualTo(expected);
        return this;
    }
}
