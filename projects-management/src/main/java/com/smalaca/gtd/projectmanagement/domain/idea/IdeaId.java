package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

public final class IdeaId {
    private final UUID id;

    private IdeaId(UUID id) {
        this.id = id;
    }

    static IdeaId from(UUID id) {
        return new IdeaId(id);
    }

    public UUID value() {
        return id;
    }
}
