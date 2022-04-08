package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.shared.libraries.validation.constrains.AtLeastOneNotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Builder
@AtLeastOneNotEmpty(
        fields = {"title", "description"},
        message = "Title or description cannot be empty."
)
@Getter
@EqualsAndHashCode
final class IdeaDto {
    private final UUID id;
    private final String title;
    private final String description;
    private final Set<CollaboratorDto> collaborators;

    CreateIdeaCommand asCreateIdeaCommand(UUID authorId) {
        return CreateIdeaCommand.create(authorId, title, description);
    }
}
