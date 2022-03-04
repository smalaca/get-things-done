package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;

@DataJpaTest
@IntegrationTest
class JpaIdeaRepositoryIntegrationTest {
    @Autowired private SpringDataJpaIdeaRepository springDataJpaIdeaRepository;
    private JpaIdeaRepository jpaIdeaRepository;

    private final IdeaTestFactory factory = new IdeaTestFactory();
    private final List<UUID> ids = new ArrayList<>();

    @BeforeEach
    void initRepository() {
        jpaIdeaRepository = new JpaIdeaRepository(springDataJpaIdeaRepository);
    }

    @AfterEach
    void deleteCreatedIdea() {
        ids.forEach(springDataJpaIdeaRepository::deleteById);
    }

    @Test
    void shouldSaveIdeas() {
        UUID titleAndDescriptionId = createIdea("Idea", "Have to be great");
        UUID noTitleId = createIdea(null, "Without a lot of information I will lost an idea");
        UUID noDescriptionId = createIdea("Create a project", null);

        assertThat(findBy(titleAndDescriptionId))
                .hasTitle("Idea")
                .hasDescription("Have to be great");
        assertThat(findBy(noTitleId))
                .hasNoTitle()
                .hasDescription("Without a lot of information I will lost an idea");
        assertThat(findBy(noDescriptionId))
                .hasTitle("Create a project")
                .hasNoDescription();
    }

    private UUID createIdea(String title, String description) {
        UUID id = jpaIdeaRepository.save(factory.create(title, description));
        ids.add(id);
        return id;
    }

    private Idea findBy(UUID id) {
        return springDataJpaIdeaRepository.findById(id).get();
    }
}