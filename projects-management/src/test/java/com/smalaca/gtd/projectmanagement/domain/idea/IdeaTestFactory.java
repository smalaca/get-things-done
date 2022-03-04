package com.smalaca.gtd.projectmanagement.domain.idea;

public class IdeaTestFactory {
    private final IdeaFactory factory = new IdeaFactory();

    public Idea create(String title, String description) {
        return factory.create(new CreateIdeaCommand(title, description));
    }
}