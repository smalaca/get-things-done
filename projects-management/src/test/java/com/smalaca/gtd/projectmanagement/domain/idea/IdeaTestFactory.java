package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

public class IdeaTestFactory {
    private final IdeaFactory factory = new IdeaFactory();

    Idea random() {
        return create(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public Idea create(UUID authorId, String title, String description) {
        return factory.create(CreateIdeaCommand.create(authorId, title, description));
    }
}