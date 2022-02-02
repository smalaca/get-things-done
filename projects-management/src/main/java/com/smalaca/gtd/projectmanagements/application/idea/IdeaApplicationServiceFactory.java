package com.smalaca.gtd.projectmanagements.application.idea;

import com.smalaca.gtd.projectmanagements.domain.idea.IdeaFactory;
import com.smalaca.gtd.projectmanagements.domain.idea.IdeaRepository;

class IdeaApplicationServiceFactory {
    IdeaApplicationService ideaApplicationService(IdeaRepository repository) {
        return new IdeaApplicationService(repository, new IdeaFactory());
    }
}
