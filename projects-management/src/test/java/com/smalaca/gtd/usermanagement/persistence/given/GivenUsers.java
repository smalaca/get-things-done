package com.smalaca.gtd.usermanagement.persistence.given;

import com.smalaca.gtd.usermanagement.persistence.user.UserRepository;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GivenUsers {
    private final UserRepository userRepository;
    private final UserTestRepository userTestRepository;
    private final List<UUID> ids = new ArrayList<>();

    GivenUsers(UserRepository userRepository, UserTestRepository userTestRepository) {
        this.userRepository = userRepository;
        this.userTestRepository = userTestRepository;
    }

    public void deleteAll() {
        ids.forEach(userTestRepository::deleteBy);
    }

    public UUID existing(String userName, String password) {
        UUID id = create(userName, password);
        ids.add(id);
        return id;
    }

    private UUID create(String userName, String password) {
        String id = userRepository.save(UserTestFactory.user(userName, password));
        return UUID.fromString(id);
    }
}
