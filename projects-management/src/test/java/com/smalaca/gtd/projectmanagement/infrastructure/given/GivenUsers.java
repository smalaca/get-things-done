package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GivenUsers {
    private final UserTestRepository userTestRepository;
    private final List<UUID> ids = new ArrayList<>();

    GivenUsers(UserTestRepository userTestRepository) {
        this.userTestRepository = userTestRepository;
    }

    public void deleteAll() {
        ids.forEach(userTestRepository::deleteBy);
    }

    public UUID existing(String userName, String password) {
        UUID id = userTestRepository.save(UserTestFactory.user(userName, password));
        ids.add(id);

        return id;
    }
}
