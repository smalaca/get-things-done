package com.smalaca.gtd.projectmanagement.domain.owner;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class OwnerId {
    @Column(name = "owner_id")
    private UUID id;

    public static OwnerId from(UUID id) {
        OwnerId ownerId = new OwnerId();
        ownerId.id = id;
        return ownerId;
    }
}
