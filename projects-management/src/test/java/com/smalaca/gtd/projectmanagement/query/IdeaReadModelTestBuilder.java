package com.smalaca.gtd.projectmanagement.query;

import com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModel;
import com.smalaca.gtd.projectmanagement.query.user.UserReadModel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@NoArgsConstructor(access = PACKAGE)
public class IdeaReadModelTestBuilder {
    private static final GivenReadModel GIVEN = new GivenReadModel();
    private final Set<UserReadModel> collaborators = new HashSet<>();
    private UUID id;
    private String title;
    private String description;

    public IdeaReadModelTestBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public IdeaReadModelTestBuilder title(String title) {
        this.title = title;
        return this;
    }

    public IdeaReadModelTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public IdeaReadModelTestBuilder collaborator(UUID id, String userName) {
        UserReadModel collaborator = GIVEN.user().id(id).userName(userName).build();
        collaborators.add(collaborator);

        return this;
    }

    public IdeaReadModel build() {
        IdeaReadModel idea = mock(IdeaReadModel.class);
        given(idea.getId()).willReturn(id);
        given(idea.getTitle()).willReturn(title);
        given(idea.getDescription()).willReturn(description);
        given(idea.getCollaborators()).willReturn(collaborators);

        return idea;
    }
}
