package com.smalaca.gtd.projectmanagement.domain.idea;

import java.util.UUID;

public interface IdeaRepository {
    UUID save(Idea idea);
}
