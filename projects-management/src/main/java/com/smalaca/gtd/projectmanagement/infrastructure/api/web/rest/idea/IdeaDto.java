package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
final class IdeaDto {
    private final UUID id;
    private final String title;
    private final String description;
    private final Set<CollaboratorDto> collaborators;
}
