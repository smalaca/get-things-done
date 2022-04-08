package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

@RequiredArgsConstructor
public final class ShareIdeaCommand {
    private final UUID authorId;
    private final UUID ideaId;
    private final UUID collaboratorId;

    AuthorId getAuthorId() {
        return AuthorId.from(authorId);
    }

    IdeaId getIdeaId() {
        return IdeaId.from(ideaId);
    }

    CollaboratorId getCollaboratorId() {
        return CollaboratorId.from(collaboratorId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShareIdeaCommand that = (ShareIdeaCommand) o;

        return new EqualsBuilder()
                .append(authorId, that.authorId)
                .append(ideaId, that.ideaId)
                .append(collaboratorId, that.collaboratorId)
                .isEquals();
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(authorId)
                .append(ideaId)
                .append(collaboratorId)
                .toHashCode();
    }
}
