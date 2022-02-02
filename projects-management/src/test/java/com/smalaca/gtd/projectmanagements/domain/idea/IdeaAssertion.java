package com.smalaca.gtd.projectmanagements.domain.idea;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class IdeaAssertion {
    private final Idea actual;

    private IdeaAssertion(Idea actual) {
        this.actual = actual;
    }

    public static IdeaAssertion assertThat(Idea actual) {
        return new IdeaAssertion(actual);
    }

    IdeaAssertion hasNoTitle() {
        return this;
    }

    public IdeaAssertion hasTitle(String expected) {
        title().isEqualTo(expected);
        return this;
    }

    private AbstractObjectAssert<?, ?> title() {
        return Assertions.assertThat(actual).extracting("title");
    }

    IdeaAssertion hasNoDescription() {
        description().isNull();
        return this;
    }

    public IdeaAssertion hasDescription(String expected) {
        description().isEqualTo(expected);
        return this;
    }

    private AbstractObjectAssert<?, ?> description() {
        return Assertions.assertThat(actual).extracting("description");
    }
}
