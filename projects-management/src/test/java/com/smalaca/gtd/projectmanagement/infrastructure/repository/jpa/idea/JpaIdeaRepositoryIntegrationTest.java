package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;

@DataJpaTest
@IntegrationTest
@Import(IdeaTestRepository.class)
class JpaIdeaRepositoryIntegrationTest {
    @Autowired private IdeaTestRepository ideaTestRepository;
    @Autowired private SpringDataJpaIdeaRepository springDataJpaIdeaRepository;
    private JpaIdeaRepository jpaIdeaRepository;

    private final IdeaTestFactory factory = new IdeaTestFactory();
    private final List<IdeaId> ids = new ArrayList<>();

    @BeforeEach
    void initRepository() {
        jpaIdeaRepository = new JpaIdeaRepository(springDataJpaIdeaRepository);
    }

    @AfterEach
    void deleteCreatedIdea() {
        ids.forEach(ideaTestRepository::deleteById);
    }

    @Test
    void shouldSaveIdeas() {
        IdeaId titleAndDescriptionId = createIdea("Idea", "Have to be great");
        IdeaId noTitleId = createIdea(null, "Without a lot of information I will lost an idea");
        IdeaId noDescriptionId = createIdea("Create a project", null);

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

    private IdeaId createIdea(String title, String description) {
        IdeaId id = jpaIdeaRepository.save(factory.create(title, description));
        ids.add(id);
        return id;
    }

    private Idea findBy(IdeaId id) {
        return ideaTestRepository.findById(id);
    }
}