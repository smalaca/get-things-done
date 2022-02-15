package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import org.assertj.core.api.Assertions;

public class ValidationFieldErrorTestDtoAssertion {
    private final ValidationFieldErrorTestDto actual;

    ValidationFieldErrorTestDtoAssertion(ValidationFieldErrorTestDto actual) {
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
