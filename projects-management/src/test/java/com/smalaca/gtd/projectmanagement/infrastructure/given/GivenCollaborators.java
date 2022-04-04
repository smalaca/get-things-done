package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;

import java.util.UUID;

import static com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder.randomUser;

public class GivenCollaborators {
    private final GivenUsers givenUsers;

    GivenCollaborators(GivenUsers givenUsers) {
        this.givenUsers = givenUsers;
    }

    public void deleteAll() {
        givenUsers.deleteAll();
    }

    public CollaboratorId existing() {
        UUID id = givenUsers.existing(randomUser());
        return CollaboratorId.from(id);
    }
}
