package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;

import java.util.UUID;

public class GivenCollaborators {
    private final GivenUsers givenUsers;

    GivenCollaborators(GivenUsers givenUsers) {
        this.givenUsers = givenUsers;
    }

    public void deleteAll() {
        givenUsers.deleteAll();
    }

    public CollaboratorId existing() {
        String name = randomString();
        String password = randomString();
        UUID id = givenUsers.existing(name, password);

        return CollaboratorId.from(id);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
