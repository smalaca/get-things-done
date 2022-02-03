package com.smalaca.gtd.projectmanagements.application.idea;

import com.smalaca.gtd.projectmanagements.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagements.domain.idea.Idea;
import com.smalaca.gtd.projectmanagements.domain.idea.IdeaFactory;
import com.smalaca.gtd.projectmanagements.domain.idea.IdeaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

public class IdeaApplicationService {
    private final IdeaRepository repository;
    private final IdeaFactory factory;

    IdeaApplicationService(IdeaRepository repository, IdeaFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Transactional
    public UUID create(CreateIdeaCommand command) {
        Idea idea = factory.create(command);

        return repository.save(idea);
    }
}
