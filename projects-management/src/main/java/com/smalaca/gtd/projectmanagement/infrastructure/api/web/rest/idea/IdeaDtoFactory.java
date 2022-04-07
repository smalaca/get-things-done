package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModel;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

class IdeaDtoFactory {
    IdeaDto create(IdeaReadModel ideaReadModel) {
        return IdeaDto.builder()
                .id(ideaReadModel.getId())
                .title(ideaReadModel.getTitle())
                .description(ideaReadModel.getDescription())
                .collaborators(collaboratorsFrom(ideaReadModel))
                .build();
    }

    private Set<CollaboratorDto> collaboratorsFrom(IdeaReadModel ideaReadModel) {
        return ideaReadModel.getCollaborators().stream()
                .map(CollaboratorDto::asDto)
                .collect(toSet());
    }
}
