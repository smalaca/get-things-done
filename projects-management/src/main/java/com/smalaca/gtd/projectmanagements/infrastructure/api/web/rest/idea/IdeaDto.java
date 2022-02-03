package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagements.domain.idea.CreateIdeaCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class IdeaDto {
    private final String title;
    private final String description;

    CreateIdeaCommand asCreateIdeaCommand() {
        return new CreateIdeaCommand(title, description);
    }
}
