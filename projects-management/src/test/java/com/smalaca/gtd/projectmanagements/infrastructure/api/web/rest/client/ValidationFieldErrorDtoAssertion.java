package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import org.assertj.core.api.Assertions;

public class ValidationFieldErrorDtoAssertion {
    private final ValidationFieldErrorDto actual;

    ValidationFieldErrorDtoAssertion(ValidationFieldErrorDto actual) {
        this.actual = actual;
    }

    public ValidationFieldErrorDtoAssertion hasFields(String... expected) {
        Assertions.assertThat(actual.getFields()).containsExactlyInAnyOrder(expected);
        return this;
    }

    public ValidationFieldErrorDtoAssertion hasMessage(String expected) {
        Assertions.assertThat(actual.getMessage()).isEqualTo(expected);
        return null;
    }
}
