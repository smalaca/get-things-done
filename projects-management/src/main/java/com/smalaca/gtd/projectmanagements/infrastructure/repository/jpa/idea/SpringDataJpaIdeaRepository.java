package com.smalaca.gtd.projectmanagements.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagements.domain.idea.Idea;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface SpringDataJpaIdeaRepository extends CrudRepository<Idea, UUID> {
}
