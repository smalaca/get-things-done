package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;

public class IdeaTestRepository {
    private final SpringDataJpaIdeaRepository repository;

    public IdeaTestRepository(SpringDataJpaIdeaRepository repository) {
        this.repository = repository;
    }

    public void deleteById(IdeaId id) {
        repository.deleteById(id.value());
    }

    Idea findById(IdeaId id) {
        return repository.findById(id.value()).get();
    }

    public IdeaId save(Idea idea) {
        return repository.save(idea).id();
    }
}