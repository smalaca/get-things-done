package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaFactory;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;

import java.util.UUID;

public class IdeaApplicationService {
    private final IdeaRepository ideaRepository;
    private final IdeaFactory factory;
    private final CollaboratorRepository collaboratorRepository;

    IdeaApplicationService(IdeaRepository ideaRepository, IdeaFactory factory, CollaboratorRepository collaboratorRepository) {
        this.ideaRepository = ideaRepository;
        this.factory = factory;
        this.collaboratorRepository = collaboratorRepository;
    }

    public UUID create(CreateIdeaCommand command) {
        Idea idea = factory.create(command);

        return ideaRepository.save(idea).value();
    }

    void share(ShareIdeaCommand command) {
        Idea idea = ideaRepository.findBy(command.getAuthorId(), command.getIdeaId());

        idea.share(collaboratorRepository, command.getCollaboratorId());

        ideaRepository.save(idea);
    }
}
