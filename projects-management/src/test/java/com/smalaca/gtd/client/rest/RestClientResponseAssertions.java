package com.smalaca.gtd.client.rest;

import com.smalaca.gtd.client.rest.idea.IdeaTestDto;
import com.smalaca.gtd.client.rest.idea.IdeaTestDtoAssertion;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDtoAssertion;
import com.smalaca.gtd.client.rest.validation.ValidationFieldErrorTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationFieldErrorTestDtoAssertion;

public class RestClientResponseAssertions {
    public static IdeaTestDtoAssertion assertThat(IdeaTestDto actual) {
        return new IdeaTestDtoAssertion(actual);
    }

    public static ValidationErrorsTestDtoAssertion assertThat(ValidationErrorsTestDto actual) {
        return new ValidationErrorsTestDtoAssertion(actual);
    }

    public static ValidationFieldErrorTestDtoAssertion assertThat(ValidationFieldErrorTestDto actual) {
        return new ValidationFieldErrorTestDtoAssertion(actual);
    }
}
