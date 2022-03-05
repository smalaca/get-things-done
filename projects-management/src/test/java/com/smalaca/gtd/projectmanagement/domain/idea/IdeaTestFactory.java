package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

public class IdeaTestFactory {
    private final IdeaFactory factory = new IdeaFactory();

    public Idea create(UUID ownerId, String title, String description) {
        return factory.create(CreateIdeaCommand.create(ownerId, title, description));
    }
}