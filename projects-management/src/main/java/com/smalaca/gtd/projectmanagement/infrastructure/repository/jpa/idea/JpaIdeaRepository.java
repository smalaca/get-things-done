package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaIdeaRepository implements IdeaRepository {
    private final SpringDataJpaIdeaRepository repository;

    JpaIdeaRepository(SpringDataJpaIdeaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID save(Idea idea) {
        return repository.save(idea).id();
    }
}
