package com.smalaca.gtd.projectmanagement.query.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface UserQueryRepository extends CrudRepository<UserReadModel, UUID> {
    UserReadModel findByUserName(String userName);
}
