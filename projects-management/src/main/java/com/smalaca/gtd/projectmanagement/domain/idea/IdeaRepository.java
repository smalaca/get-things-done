package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;

public interface IdeaRepository {
    IdeaId save(Idea idea);

    Idea findBy(AuthorId authorId, IdeaId ideaId);
}
