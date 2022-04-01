package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.given;

import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaTestRepository;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.JpaIdeaRepository;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@TestConfiguration
@Import({
        UserTestRepository.class,
        IdeaTestRepository.class, JpaIdeaRepository.class})
public class GivenTestConfiguration {
    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public GivenCollaborators givenCollaborators(UserTestRepository userTestRepository) {
        return new GivenCollaborators(userTestRepository);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public GivenIdeas givenIdeas(IdeaRepository ideaRepository, IdeaTestRepository ideaTestRepository) {
        return new GivenIdeas(ideaRepository, ideaTestRepository, new IdeaTestFactory());
    }
}
