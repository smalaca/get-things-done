package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.given;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GivenCollaborators {
    private final UserTestRepository userTestRepository;
    private final List<UUID> ids = new ArrayList<>();

    public GivenCollaborators(UserTestRepository userTestRepository) {
        this.userTestRepository = userTestRepository;
    }

    public void deleteAll() {
        ids.forEach(userTestRepository::deleteBy);
    }

    public CollaboratorId existing() {
        String name = randomString();
        String password = randomString();
        UUID id = userTestRepository.save(UserTestFactory.user(name, password));
        ids.add(id);

        return CollaboratorId.from(id);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
