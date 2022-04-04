package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;

import java.util.UUID;

public class IdeaTestBuilder {
    private static final IdeaFactory FACTORY = new IdeaFactory();

    private final AuthorId authorId;
    private String title;
    private String description;

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

    public Idea build() {
        return FACTORY.create(asCreateCommand());
    }

    private CreateIdeaCommand asCreateCommand() {
        return CreateIdeaCommand.create(authorId.value(), title, description);
    }
}
