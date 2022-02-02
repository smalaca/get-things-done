package com.smalaca.gtd.projectmanagements.domain.idea;

import java.util.UUID;

public interface IdeaRepository {
    UUID save(Idea idea);
}
