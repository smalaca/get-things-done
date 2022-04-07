package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ShareIdeaCommand {
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
}
