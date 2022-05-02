package com.smalaca.gtd.projectmanagement.domain.collaborator;

public class CollaboratorDoesNotExistException extends RuntimeException {
    public CollaboratorDoesNotExistException(CollaboratorId collaboratorId) {
        super("Collaborator: " + collaboratorId + " does not exist.");
    }
}
