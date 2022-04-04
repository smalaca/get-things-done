package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;

public class IdeaTestRepository {
    private final SpringDataJpaIdeaRepository repository;

    public IdeaTestRepository(SpringDataJpaIdeaRepository repository) {
        this.repository = repository;
    }

    public void deleteById(IdeaId id) {
        repository.deleteById(id.value());
    }
}