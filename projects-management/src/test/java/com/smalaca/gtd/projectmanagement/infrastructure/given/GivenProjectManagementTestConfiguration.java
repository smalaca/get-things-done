package com.smalaca.gtd.projectmanagement.infrastructure.given;

import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaTestRepository;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.JpaIdeaRepository;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUserManagementTestConfiguration;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@TestConfiguration
@Import({
        GivenUserManagementTestConfiguration.class,
        IdeaTestRepository.class, JpaIdeaRepository.class})
public class GivenProjectManagementTestConfiguration {
    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public GivenCollaborators givenCollaborators(GivenUsers givenUsers) {
        return new GivenCollaborators(givenUsers);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public GivenIdeas givenIdeas(IdeaRepository ideaRepository, IdeaTestRepository ideaTestRepository) {
        return new GivenIdeas(ideaRepository, ideaTestRepository, new IdeaTestFactory());
    }
}
