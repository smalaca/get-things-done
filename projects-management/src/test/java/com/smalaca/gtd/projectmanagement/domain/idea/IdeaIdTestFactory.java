package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

public class IdeaIdTestFactory {
    public static IdeaId ideaIdFrom(UUID id) {
        return IdeaId.from(id);
    }
}