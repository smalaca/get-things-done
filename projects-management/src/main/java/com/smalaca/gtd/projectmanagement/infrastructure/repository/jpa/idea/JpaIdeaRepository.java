package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaIdeaRepository implements IdeaRepository {
    private final SpringDataJpaIdeaRepository repository;

    JpaIdeaRepository(SpringDataJpaIdeaRepository repository) {
        this.repository = repository;
    }

    @Override
    public IdeaId save(Idea idea) {
        return repository.save(idea).id();
    }
}
