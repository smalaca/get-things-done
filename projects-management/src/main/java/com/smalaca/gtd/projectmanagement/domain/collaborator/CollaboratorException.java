package com.smalaca.gtd.projectmanagement.domain.collaborator;

public class CollaboratorException extends RuntimeException {
    public CollaboratorException(CollaboratorId collaboratorId) {
        super("Collaborator: " + collaboratorId + " does not exist.");
    }
}
