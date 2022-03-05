package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.shared.validation.constrains.AtLeastOneNotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AtLeastOneNotEmpty(
        fields = {"title", "description"},
        message = "Title or description cannot be empty."
)
@Getter
class IdeaDto {
    private final UUID id;
    private final String title;
    private final String description;

    CreateIdeaCommand asCreateIdeaCommand(UUID ownerId) {
        return CreateIdeaCommand.create(ownerId, title, description);
    }
}
