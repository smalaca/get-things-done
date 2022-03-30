package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.collaborator;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface SpringDataJpaUserRepository extends CrudRepository<User, UUID> {
}
