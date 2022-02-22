package com.smalaca.gtd.projectmanagement.query.idea;

import org.assertj.core.api.Assertions;

class IdeaReadModelAssertion {
    private final IdeaReadModel actual;

    private IdeaReadModelAssertion(IdeaReadModel actual) {
        this.actual = actual;
    }

    static IdeaReadModelAssertion assertThat(IdeaReadModel actual) {
        return new IdeaReadModelAssertion(actual);
    }

    IdeaReadModelAssertion hasTitle(String expected) {
        Assertions.assertThat(actual.getTitle()).isEqualTo(expected);
        return this;
    }

    IdeaReadModelAssertion hasDescription(String expected) {
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected);
        return this;
    }

    IdeaReadModelAssertion hasNoDescription() {
        Assertions.assertThat(actual.getDescription()).isNull();
        return this;
    }

    IdeaReadModelAssertion hasNoTitle() {
        Assertions.assertThat(actual.getTitle()).isNull();
        return this;
    }
}
