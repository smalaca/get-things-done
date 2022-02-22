package com.smalaca.gtd.client.rest;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorsTestDtoAssertion {
    private final ValidationErrorsTestDto actual;

    ValidationErrorsTestDtoAssertion(ValidationErrorsTestDto actual) {
        this.actual = actual;
    }

    public ValidationErrorsTestDtoAssertion hasOneError() {
        assertThat(actual.getErrors()).hasSize(1);
        return this;
    }

    public ValidationErrorsTestDtoAssertion hasErrorThat(Consumer<ValidationFieldErrorTestDto> requirements) {
        assertThat(actual.getErrors()).anySatisfy(requirements);
        return this;
    }
}
