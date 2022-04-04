package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

public class IdeaTestBuilder {
    private static final IdeaFactory FACTORY = new IdeaFactory();

    private UUID authorId;
    private String title;
    private String description;

    static IdeaTestBuilder idea() {
        return new IdeaTestBuilder();
    }

    static IdeaTestBuilder randomIdea() {
        IdeaTestBuilder builder = idea();
        builder.authorId = UUID.randomUUID();
        builder.title = UUID.randomUUID().toString();
        builder.description = UUID.randomUUID().toString();
        return builder;
    }

    IdeaTestBuilder authorId(UUID authorId) {
        this.authorId = authorId;
        return this;
    }

    IdeaTestBuilder title(String title) {
        this.title = title;
        return this;
    }

    IdeaTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    CreateIdeaCommand asCreateCommand() {
        return CreateIdeaCommand.create(authorId, title, description);
    }

    Idea build() {
        return FACTORY.create(asCreateCommand());
    }
}
