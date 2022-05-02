package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.shared.libraries.validation.constrains.AtLeastOneNotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@AtLeastOneNotEmpty(
        fields = {"title", "description"},
        message = "Title or description cannot be empty."
)
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
final class IdeaCreateDto {
    private final String title;
    private final String description;

    CreateIdeaCommand asCommand(UUID authorId) {
        return CreateIdeaCommand.create(authorId, title, description);
    }
}
