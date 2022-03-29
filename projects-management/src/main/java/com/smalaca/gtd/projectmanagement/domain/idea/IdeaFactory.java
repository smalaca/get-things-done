package com.smalaca.gtd.projectmanagement.domain.idea;

public class IdeaFactory {
    public Idea create(CreateIdeaCommand command) {
        if (isInvalid(command)) {
            throw new IdeaException("Cannot create idea without title and description");
        }

        return new Idea(command.getAuthorId(), command.getTitle(), command.getDescription());
    }

    private boolean isInvalid(CreateIdeaCommand command) {
        return command.hasNoTitle() && command.hasNoDescription();
    }
}
