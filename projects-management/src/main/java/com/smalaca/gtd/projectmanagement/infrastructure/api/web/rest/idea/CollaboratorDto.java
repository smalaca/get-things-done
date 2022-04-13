package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.query.user.UserReadModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
public final class CollaboratorDto {
    private final UUID id;
    private final String userName;

    static CollaboratorDto asDto(UserReadModel user) {
        return new CollaboratorDto(user.getId(), user.getUserName());
    }
}
