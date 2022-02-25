package com.smalaca.gtd.client.rest.validation;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorsTestDtoAssertion {
    private final ValidationErrorsTestDto actual;

    public ValidationErrorsTestDtoAssertion(ValidationErrorsTestDto actual) {
        this.actual = actual;
    }

    public ValidationErrorsTestDtoAssertion hasOneError() {
        return hasErrors(1);
    }

    public ValidationErrorsTestDtoAssertion hasErrors(int expected) {
        assertThat(actual.getErrors()).hasSize(expected);
        return this;
    }

    public ValidationErrorsTestDtoAssertion hasErrorThat(Consumer<ValidationFieldErrorTestDto> requirements) {
        assertThat(actual.getErrors()).anySatisfy(requirements);
        return this;
    }
}
