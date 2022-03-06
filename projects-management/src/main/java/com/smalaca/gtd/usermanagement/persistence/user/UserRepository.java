package com.smalaca.gtd.usermanagement.persistence.user;

import com.smalaca.gtd.usermanagement.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final CrudUserRepository repository;

    UserRepository(CrudUserRepository repository) {
        this.repository = repository;
    }

    public String save(User user) {
        return repository.save(user).id();
    }

    public boolean exists(String userName) {
        return repository.existsByUserName(userName);
    }
}
