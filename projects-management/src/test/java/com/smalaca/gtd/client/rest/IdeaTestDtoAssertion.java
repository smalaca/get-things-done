package com.smalaca.gtd.client.rest;

import org.assertj.core.api.Assertions;

public class IdeaTestDtoAssertion {
    private final IdeaTestDto actual;

    IdeaTestDtoAssertion(IdeaTestDto actual) {
        this.actual = actual;
    }

    public IdeaTestDtoAssertion hasTitle(String expected) {
        Assertions.assertThat(actual.getTitle()).isEqualTo(expected);
        return this;
    }

    public IdeaTestDtoAssertion hasNoTitle() {
        Assertions.assertThat(actual.getTitle()).isNull();
        return this;
    }

    public IdeaTestDtoAssertion hasDescription(String expected) {
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected);
        return this;
    }

    public IdeaTestDtoAssertion hasNoDescription() {
        Assertions.assertThat(actual.getDescription()).isNull();
        return this;
    }
}
