package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorsDtoAssertion {
    private final ValidationErrorsDto actual;

    ValidationErrorsDtoAssertion(ValidationErrorsDto actual) {
        this.actual = actual;
    }

    public ValidationErrorsDtoAssertion hasOneError() {
        assertThat(actual.getErrors()).hasSize(1);
        return this;
    }

    public ValidationErrorsDtoAssertion hasErrorThat(Consumer<ValidationFieldErrorDto> requirements) {
        assertThat(actual.getErrors()).anySatisfy(requirements);
        return this;
    }
}
