package com.smalaca.gtd.usermanagement.domain.user;

import java.util.UUID;

public class UserTestRepository {
    private final CrudUserRepository repository;

    UserTestRepository(CrudUserRepository repository) {
        this.repository = repository;
    }

    public User findBy(UUID id) {
        return repository.findById(id).get();
    }

    public void deleteBy(UUID id) {
        repository.deleteById(id);
    }
}