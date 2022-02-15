package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

public class ResponseAssertions {
    public static ValidationErrorsTestDtoAssertion assertThat(ValidationErrorsTestDto actual) {
        return new ValidationErrorsTestDtoAssertion(actual);
    }

    public static ValidationFieldErrorTestDtoAssertion assertThat(ValidationFieldErrorTestDto actual) {
        return new ValidationFieldErrorTestDtoAssertion(actual);
    }
}
