package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaFactory;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;

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
