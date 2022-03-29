package com.smalaca.gtd.projectmanagement.domain.author;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class AuthorId {
    @Column(name = "author_id")
    private UUID id;

    public static AuthorId from(UUID id) {
        AuthorId authorId = new AuthorId();
        authorId.id = id;
        return authorId;
    }
}
