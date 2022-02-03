package com.smalaca.gtd.projectmanagements.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagements.domain.idea.Idea;

import java.util.UUID;

public class IdeaTestRepository {
    private final SpringDataJpaIdeaRepository repository;

    public IdeaTestRepository(SpringDataJpaIdeaRepository repository) {
        this.repository = repository;
    }

    public Idea findById(UUID id) {
        return repository.findById(id).get();
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}