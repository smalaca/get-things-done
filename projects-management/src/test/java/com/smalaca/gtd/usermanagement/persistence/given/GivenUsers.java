package com.smalaca.gtd.usermanagement.persistence.given;

import com.smalaca.gtd.usermanagement.persistence.user.UserRepository;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder;
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

    public UUID existing(UserTestBuilder user) {
        String id = userRepository.save(user.build());
        UUID uuid = UUID.fromString(id);
        existing(uuid);

        return uuid;
    }

    public void existing(UUID id) {
        ids.add(id);
    }
}
