package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.application.idea.ShareIdeaCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
class IdeaShareDto {
    private UUID collaboratorId;

    ShareIdeaCommand asShareIdeaCommand(UUID authorId, UUID ideaId) {
        return new ShareIdeaCommand(authorId, ideaId, collaboratorId);
    }
}
