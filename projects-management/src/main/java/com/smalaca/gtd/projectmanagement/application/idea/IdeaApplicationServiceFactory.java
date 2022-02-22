package com.smalaca.gtd.projectmanagement.application.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.IdeaFactory;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdeaApplicationServiceFactory {
    @Bean
    IdeaApplicationService ideaApplicationService(IdeaRepository repository) {
        return new IdeaApplicationService(repository, new IdeaFactory());
    }
}
