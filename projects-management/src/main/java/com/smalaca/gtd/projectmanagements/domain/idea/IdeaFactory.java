package com.smalaca.gtd.projectmanagements.domain.idea;

public class IdeaFactory {
    public Idea create(CreateIdeaCommand command) {
        if (isValid(command)) {
            throw new IdeaException("Cannot create idea without title and description");
        }

        return new Idea(command.getTitle(), command.getDescription());
    }

    private boolean isValid(CreateIdeaCommand command) {
        return command.hasTitle() && command.hasDescription();
    }
}
