package com.smalaca.gtd.projectmanagement.domain.collaborator;

public interface CollaboratorRepository {
    boolean existsBy(CollaboratorId collaboratorId);
}
