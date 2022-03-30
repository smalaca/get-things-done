package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaIdTestFactory;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void shouldRecognizeWhenIdeaDoesNotExist() {
        AuthorId authorWithoutIdeaId = randomId();
        AuthorId authorWithIdeaId = randomId();
        IdeaId ideaId1 = createIdea(authorWithIdeaId, "Idea", "Have to be great");
        IdeaId ideaId2 = createIdea(randomId(), "Idea Two", "Have to be even greater");
        IdeaId notExistingIdeaId = IdeaIdTestFactory.ideaIdFrom(UUID.randomUUID());

        assertThatIdeaDoesNotExist(authorWithoutIdeaId, ideaId1);
        assertThatIdeaDoesNotExist(authorWithIdeaId, notExistingIdeaId);
        assertThatIdeaDoesNotExist(authorWithIdeaId, ideaId2);
    }

    private void assertThatIdeaDoesNotExist(AuthorId authorId, IdeaId ideaId) {
        Executable executable = () -> jpaIdeaRepository.findBy(authorId, ideaId);

        IdeaDoesNotExistException actual = assertThrows(IdeaDoesNotExistException.class, executable);
        assertThat(actual)
                .hasMessage("Idea for Author: " + authorId.value() + ", and Idea: " + ideaId.value() + " does not exist.");
    }

    @Test
    void shouldSaveIdeas() {
        AuthorId authorId1 = randomId();
        AuthorId authorId2 = randomId();
        AuthorId authorId3 = randomId();
        IdeaId titleAndDescriptionId = createIdea(authorId1, "Idea", "Have to be great");
        IdeaId noTitleId = createIdea(authorId2, null, "Without a lot of information I will lost an idea");
        IdeaId noDescriptionId = createIdea(authorId3, "Create a project", null);

        assertThat(findBy(authorId1, titleAndDescriptionId))
                .hasAuthorId(authorId1)
                .hasTitle("Idea")
                .hasDescription("Have to be great");
        assertThat(findBy(authorId2, noTitleId))
                .hasAuthorId(authorId2)
                .hasNoTitle()
                .hasDescription("Without a lot of information I will lost an idea");
        assertThat(findBy(authorId3, noDescriptionId))
                .hasAuthorId(authorId3)
                .hasTitle("Create a project")
                .hasNoDescription();
    }

    private AuthorId randomId() {
        return AuthorId.from(UUID.randomUUID());
    }

    private IdeaId createIdea(AuthorId authorId, String title, String description) {
        Idea idea = factory.create(authorId.value(), title, description);
        IdeaId id = jpaIdeaRepository.save(idea);
        ids.add(id);

        return id;
    }

    private Idea findBy(AuthorId authorId, IdeaId id) {
        return jpaIdeaRepository.findBy(authorId, id);
    }
}