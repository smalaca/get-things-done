package com.smalaca.gtd.projectmanagement.domain.collaborator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class CollaboratorId {
    @Column(name = "collaborator_id")
    private UUID id;

    public static CollaboratorId from(UUID id) {
        CollaboratorId collaboratorId = new CollaboratorId();
        collaboratorId.id = id;
        return collaboratorId;
    }

    public UUID value() {
        return id;
    }
}
