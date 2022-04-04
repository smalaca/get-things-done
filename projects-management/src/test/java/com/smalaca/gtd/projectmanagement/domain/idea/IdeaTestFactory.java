package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestBuilder.idea;

public class IdeaTestFactory {
    Idea random() {
        return IdeaTestBuilder.randomIdea().build();
    }

    public Idea create(UUID authorId, String title, String description) {
        return idea()
                .authorId(authorId)
                .title(title)
                .description(description)
                .build();
    }
}