package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaTestRepository;

import java.util.ArrayList;
import java.util.List;

public class GivenIdeas {
    private static final String NO_DESCRIPTION = null;

    private final IdeaRepository ideaRepository;
    private final IdeaTestRepository ideaTestRepository;
    private final IdeaTestFactory ideaTestFactory;
    private final List<IdeaId> ids = new ArrayList<>();

    GivenIdeas(IdeaRepository ideaRepository, IdeaTestRepository ideaTestRepository, IdeaTestFactory ideaTestFactory) {
        this.ideaTestFactory = ideaTestFactory;
        this.ideaRepository = ideaRepository;
        this.ideaTestRepository = ideaTestRepository;
    }

    public void deleteAll() {
        ids.forEach(ideaTestRepository::deleteById);
    }

    public IdeaId existing(AuthorId authorId, String title) {
        return existing(authorId, title, NO_DESCRIPTION);
    }

    public IdeaId existing(AuthorId authorId, String title, String description) {
        Idea idea = ideaTestFactory.create(authorId.value(), title, description);
        IdeaId id = ideaRepository.save(idea);
        existing(id);

        return id;
    }

    public void existing(IdeaId id) {
        ids.add(id);
    }
}
