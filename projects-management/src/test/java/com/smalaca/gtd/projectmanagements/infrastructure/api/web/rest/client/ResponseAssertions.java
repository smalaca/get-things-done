package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

public class ResponseAssertions {
    public static ValidationErrorsDtoAssertion assertThat(ValidationErrorsDto actual) {
        return new ValidationErrorsDtoAssertion(actual);
    }

    public static ValidationFieldErrorDtoAssertion assertThat(ValidationFieldErrorDto actual) {
        return new ValidationFieldErrorDtoAssertion(actual);
    }
}
