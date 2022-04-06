package com.smalaca.gtd.projectmanagement.query.idea;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface IdeaQueryRepository extends CrudRepository<IdeaReadModel, UUID> {
    @Query("SELECT i FROM IdeaReadModel i LEFT JOIN FETCH i.collaborators c WHERE i.id = :id")
    Optional<IdeaReadModel> findOneById(UUID id);
}
