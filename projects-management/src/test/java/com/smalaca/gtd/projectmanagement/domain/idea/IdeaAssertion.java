package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;

public class IdeaAssertion {
    private final Idea actual;

    private IdeaAssertion(Idea actual) {
        this.actual = actual;
    }

    public static IdeaAssertion assertThat(Idea actual) {
        return new IdeaAssertion(actual);
    }

    public IdeaAssertion hasNoTitle() {
        return this;
    }

    public IdeaAssertion hasTitle(String expected) {
        title().isEqualTo(expected);
        return this;
    }

    private AbstractObjectAssert<?, ?> title() {
        return Assertions.assertThat(actual).extracting("title");
    }

    public IdeaAssertion hasNoDescription() {
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

    public IdeaAssertion hasAuthorId(AuthorId expected) {
        Assertions.assertThat(actual).extracting("authorId").isEqualTo(expected);
        return this;
    }

    public IdeaAssertion hasCollaborators(UUID... collaboratorIds) {
        Set<CollaboratorId> collaborators = Arrays.stream(collaboratorIds)
                .map(CollaboratorId::from)
                .collect(toSet());
        return hasCollaborators(collaborators);
    }

    public IdeaAssertion hasCollaborators(CollaboratorId... collaboratorIds) {
        return hasCollaborators(asSet(collaboratorIds));
    }

    public IdeaAssertion hasNoCollaborators() {
        return hasCollaborators(emptySet());
    }

    private IdeaAssertion hasCollaborators(Set<CollaboratorId> collaborators) {
        Assertions.assertThat(actual).extracting("collaborators").isEqualTo(collaborators);
        return this;
    }
}
