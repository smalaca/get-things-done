package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder;

import java.util.UUID;

import static com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder.randomUser;
import static com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder.user;

public class GivenCollaborators {
    private final GivenUsers givenUsers;

    GivenCollaborators(GivenUsers givenUsers) {
        this.givenUsers = givenUsers;
    }

    public void deleteAll() {
        givenUsers.deleteAll();
    }

    public CollaboratorId existing() {
        return existing(randomUser());
    }

    public CollaboratorId existing(String userName) {
        return existing(user(userName));
    }

    private CollaboratorId existing(UserTestBuilder user) {
        UUID id = givenUsers.existing(user);
        return CollaboratorId.from(id);
    }
}
