package com.smalaca.gtd.projectmanagements.application.idea;

import com.smalaca.gtd.projectmanagements.domain.idea.IdeaFactory;
import com.smalaca.gtd.projectmanagements.domain.idea.IdeaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdeaApplicationServiceFactory {
    @Bean
    IdeaApplicationService ideaApplicationService(IdeaRepository repository) {
        return new IdeaApplicationService(repository, new IdeaFactory());
    }
}
