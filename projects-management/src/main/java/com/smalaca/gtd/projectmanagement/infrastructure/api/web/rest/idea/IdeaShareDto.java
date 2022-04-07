package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.smalaca.gtd.projectmanagement.application.idea.ShareIdeaCommand;
import lombok.Getter;

import java.util.UUID;

@Getter
class IdeaShareDto {
    private final UUID collaboratorId;

    @JsonCreator
    IdeaShareDto(UUID collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    ShareIdeaCommand asShareIdeaCommand(UUID authorId, UUID ideaId) {
        return new ShareIdeaCommand(authorId, ideaId, getCollaboratorId());
    }
}
