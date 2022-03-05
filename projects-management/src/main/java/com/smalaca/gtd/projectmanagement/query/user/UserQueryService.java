package com.smalaca.gtd.projectmanagement.query.user;

import org.springframework.stereotype.Service;

@Service
public class UserQueryService {
    private final UserQueryRepository repository;

    UserQueryService(UserQueryRepository repository) {
        this.repository = repository;
    }

    public UserReadModel findByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
