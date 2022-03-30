package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface SpringDataJpaIdeaRepository extends CrudRepository<Idea, UUID> {
    Optional<Idea> findByAuthorIdIdAndId(UUID authorId, UUID ideaId);
}
