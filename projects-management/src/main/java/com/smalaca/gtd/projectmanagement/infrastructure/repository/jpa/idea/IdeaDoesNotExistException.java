package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;

public class IdeaDoesNotExistException extends RuntimeException {
    IdeaDoesNotExistException(AuthorId authorId, IdeaId ideaId) {
        super("Idea for Author: " + authorId.value() + ", and Idea: " + ideaId.value() + " does not exist.");
    }
}
