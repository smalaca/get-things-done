package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.projectmanagement.domain.owner.OwnerId;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        UUID ownerId1 = UUID.randomUUID();
        UUID ownerId2 = UUID.randomUUID();
        UUID ownerId3 = UUID.randomUUID();
        IdeaId titleAndDescriptionId = createIdea(ownerId1, "Idea", "Have to be great");
        IdeaId noTitleId = createIdea(ownerId2, null, "Without a lot of information I will lost an idea");
        IdeaId noDescriptionId = createIdea(ownerId3, "Create a project", null);

        assertThat(findBy(titleAndDescriptionId))
                .hasOwnerId(OwnerId.from(ownerId1))
                .hasTitle("Idea")
                .hasDescription("Have to be great");
        assertThat(findBy(noTitleId))
                .hasOwnerId(OwnerId.from(ownerId2))
                .hasNoTitle()
                .hasDescription("Without a lot of information I will lost an idea");
        assertThat(findBy(noDescriptionId))
                .hasOwnerId(OwnerId.from(ownerId3))
                .hasTitle("Create a project")
                .hasNoDescription();
    }

    private IdeaId createIdea(UUID ownerId, String title, String description) {
        IdeaId id = jpaIdeaRepository.save(factory.create(ownerId, title, description));
        ids.add(id);
        return id;
    }

    private Idea findBy(IdeaId id) {
        return ideaTestRepository.findById(id);
    }
}