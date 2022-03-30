package com.smalaca.gtd.projectmanagement.domain.idea;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public final class IdeaId {
    private final UUID id;

    private IdeaId(UUID id) {
        this.id = id;
    }

    public static IdeaId from(UUID id) {
        return new IdeaId(id);
    }

    public UUID value() {
        return id;
    }
}
