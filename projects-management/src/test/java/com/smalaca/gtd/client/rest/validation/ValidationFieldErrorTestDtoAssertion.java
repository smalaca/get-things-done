package com.smalaca.gtd.client.rest.validation;

import org.assertj.core.api.Assertions;

public class ValidationFieldErrorTestDtoAssertion {
    private final ValidationFieldErrorTestDto actual;

    public ValidationFieldErrorTestDtoAssertion(ValidationFieldErrorTestDto actual) {
        this.actual = actual;
    }

    public ValidationFieldErrorTestDtoAssertion hasFields(String... expected) {
        Assertions.assertThat(actual.getFields()).containsExactlyInAnyOrder(expected);
        return this;
    }

    public ValidationFieldErrorTestDtoAssertion hasMessage(String expected) {
        Assertions.assertThat(actual.getMessage()).isEqualTo(expected);
        return null;
    }
}
