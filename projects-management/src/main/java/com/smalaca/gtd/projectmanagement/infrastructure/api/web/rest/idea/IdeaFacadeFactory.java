package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.application.idea.IdeaApplicationService;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaQueryService;
import com.smalaca.gtd.projectmanagement.query.user.UserQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class IdeaFacadeFactory {
    @Bean
    IdeaFacade create(
            IdeaApplicationService ideaApplicationService, IdeaQueryService ideaQueryService, UserQueryService userQueryService) {
        return new IdeaFacade(ideaApplicationService, ideaQueryService, userQueryService, new IdeaDtoFactory());
    }
}
