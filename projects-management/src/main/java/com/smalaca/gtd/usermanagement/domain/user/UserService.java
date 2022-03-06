package com.smalaca.gtd.usermanagement.domain.user;

public class UserService {
    private final UserFactory factory;
    private final UserRepository repository;

    UserService(UserFactory factory, UserRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public String register(String userName, String password) {
        User user = factory.create(userName, password);
        return repository.save(user);
    }
}
