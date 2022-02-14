package com.smalaca.gtd.projectmanagements.query.idea;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface IdeaQueryRepository extends CrudRepository<IdeaReadModel, UUID> {

}
