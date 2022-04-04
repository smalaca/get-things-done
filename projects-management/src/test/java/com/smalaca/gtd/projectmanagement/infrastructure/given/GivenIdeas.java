package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestBuilder;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaTestRepository;

import java.util.ArrayList;
import java.util.List;

public class GivenIdeas {
    private final IdeaRepository ideaRepository;
    private final IdeaTestRepository ideaTestRepository;
    private final List<IdeaId> ids = new ArrayList<>();

    GivenIdeas(IdeaRepository ideaRepository, IdeaTestRepository ideaTestRepository) {
        this.ideaRepository = ideaRepository;
        this.ideaTestRepository = ideaTestRepository;
    }

    public void deleteAll() {
        ids.forEach(ideaTestRepository::deleteById);
    }

    public IdeaId existing(IdeaTestBuilder builder) {
        IdeaId id = ideaRepository.save(builder.build());
        existing(id);

        return id;
    }

    public void existing(IdeaId id) {
        ids.add(id);
    }
}
