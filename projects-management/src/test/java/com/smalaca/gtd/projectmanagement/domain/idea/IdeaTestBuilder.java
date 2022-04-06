package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class IdeaTestBuilder {
    private static final IdeaFactory FACTORY = new IdeaFactory();
    private static final CollaboratorRepository COLLABORATOR_REPOSITORY = new CollaboratorRepository() {
        @Override
        public boolean existsBy(CollaboratorId collaboratorId) {
            return true;
        }
    };

    private final AuthorId authorId;
    private String title;
    private String description;
    private List<CollaboratorId> collaboratorIds = new ArrayList<>();

    private IdeaTestBuilder(AuthorId authorId) {
        this.authorId = authorId;
    }

    public static IdeaTestBuilder idea(AuthorId authorId) {
        return new IdeaTestBuilder(authorId);
    }

    static IdeaTestBuilder randomIdea() {
        return idea(AuthorId.from(UUID.randomUUID()))
                .title(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString());
    }

    public IdeaTestBuilder title(String title) {
        this.title = title;
        return this;
    }

    public IdeaTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public IdeaTestBuilder collaborators(CollaboratorId... collaboratorIds) {
        this.collaboratorIds = Arrays.asList(collaboratorIds);
        return this;
    }

    public Idea build() {
        Idea idea = FACTORY.create(asCreateCommand());
        collaboratorIds.forEach(collaboratorId -> idea.share(COLLABORATOR_REPOSITORY, collaboratorId));

        return idea;
    }

    private CreateIdeaCommand asCreateCommand() {
        return CreateIdeaCommand.create(authorId.value(), title, description);
    }
}
