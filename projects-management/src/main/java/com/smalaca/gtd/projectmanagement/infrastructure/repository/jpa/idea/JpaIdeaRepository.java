package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public Idea findBy(AuthorId authorId, IdeaId ideaId) {
        Optional<Idea> found = repository.findByAuthorIdIdAndId(authorId.value(), ideaId.value());

        return found.orElseThrow(() -> {
            throw new IdeaDoesNotExistException(authorId, ideaId);
        });
    }
}
