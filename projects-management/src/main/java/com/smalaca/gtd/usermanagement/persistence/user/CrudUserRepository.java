package com.smalaca.gtd.usermanagement.persistence.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface CrudUserRepository extends CrudRepository<User, UUID> {
    boolean existsByUserName(String userName);
}
