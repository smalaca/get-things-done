package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;

@DataJpaTest
@IntegrationTest
class JpaIdeaRepositoryIntegrationTest {
    @Autowired private SpringDataJpaIdeaRepository springDataJpaIdeaRepository;
    private JpaIdeaRepository jpaIdeaRepository;

    private final IdeaTestFactory factory = new IdeaTestFactory();
    private UUID id;

    @BeforeEach
    void initRepository() {
        jpaIdeaRepository = new JpaIdeaRepository(springDataJpaIdeaRepository);
    }

    @AfterEach
    void deleteCreatedIdea() {
        if (id != null) {
            springDataJpaIdeaRepository.deleteById(id);
        }
    }

    @Test
    void shouldSaveIdeaWithAllInformation() {
        Idea idea = factory.create("Idea", "Have to be great");

        id = jpaIdeaRepository.save(idea);

        assertThat(findBy(id))
                .hasTitle("Idea")
                .hasDescription("Have to be great");
    }

    @Test
    void shouldSaveIdeaWithTitleOnly() {
        Idea idea = factory.create("Create a project", null);

        id = jpaIdeaRepository.save(idea);

        assertThat(findBy(id))
                .hasTitle("Create a project")
                .hasNoDescription();
    }

    @Test
    void shouldSaveIdeaWithDescriptionOnly() {
        Idea idea = factory.create(null, "Without a lot of information I will lost an idea");

        id = jpaIdeaRepository.save(idea);

        assertThat(findBy(id))
                .hasNoTitle()
                .hasDescription("Without a lot of information I will lost an idea");
    }

    private Idea findBy(UUID id) {
        return springDataJpaIdeaRepository.findById(id).get();
    }
}