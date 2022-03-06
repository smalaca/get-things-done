package com.smalaca.gtd.usermanagement.persistence.user;

import com.smalaca.gtd.usermanagement.domain.user.User;

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

    public UUID save(User user) {
        String saved = repository.save(user).id();
        return UUID.fromString(saved);
    }
}