package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.Idea;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaRepository;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.collaborator.JpaCollaboratorRepository;
import com.smalaca.gtd.tests.annotation.RepositoryTest;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaAssertion.assertThat;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
@Import({IdeaTestRepository.class, JpaCollaboratorRepository.class, UserTestRepository.class})
class JpaIdeaRepositoryIntegrationTest {
    private static final String NO_DESCRIPTION = null;
    private static final String NO_TITLE = null;

    @Autowired private IdeaTestRepository ideaTestRepository;
    @Autowired private SpringDataJpaIdeaRepository springDataJpaIdeaRepository;
    private final List<IdeaId> ideaIds = new ArrayList<>();
    private final List<UUID> collaboratorIds = new ArrayList<>();
    @Autowired private CollaboratorRepository collaboratorRepository;

    private final IdeaTestFactory factory = new IdeaTestFactory();
    @Autowired private UserTestRepository userTestRepository;
    private IdeaRepository ideaRepository;

    @BeforeEach
    void initRepository() {
        ideaRepository = new JpaIdeaRepository(springDataJpaIdeaRepository);
    }

    @AfterEach
    void deleteIdeasAndCollaborators() {
        ideaIds.forEach(ideaTestRepository::deleteById);
        collaboratorIds.forEach(userTestRepository::deleteBy);
    }

    @Test
    void shouldRecognizeWhenIdeaDoesNotExist() {
        AuthorId authorWithoutIdeaId = randomAuthorId();
        AuthorId authorWithIdeaId = randomAuthorId();
        IdeaId ideaId1 = createIdea(authorWithIdeaId, "Idea", "Have to be great");
        IdeaId ideaId2 = createIdea(randomAuthorId(), "Idea Two", "Have to be even greater");
        IdeaId notExistingIdeaId = IdeaId.from(UUID.randomUUID());

        assertThatIdeaDoesNotExist(authorWithoutIdeaId, ideaId1);
        assertThatIdeaDoesNotExist(authorWithIdeaId, notExistingIdeaId);
        assertThatIdeaDoesNotExist(authorWithIdeaId, ideaId2);
    }

    private void assertThatIdeaDoesNotExist(AuthorId authorId, IdeaId ideaId) {
        Executable executable = () -> ideaRepository.findBy(authorId, ideaId);

        IdeaDoesNotExistException actual = assertThrows(IdeaDoesNotExistException.class, executable);
        assertThat(actual)
                .hasMessage("Idea for Author: " + authorId.value() + ", and Idea: " + ideaId.value() + " does not exist.");
    }

    @Test
    void shouldSaveIdeas() {
        AuthorId authorId1 = randomAuthorId();
        AuthorId authorId2 = randomAuthorId();
        AuthorId authorId3 = randomAuthorId();
        IdeaId titleAndDescriptionId = createIdea(authorId1, "Idea", "Have to be great");
        IdeaId noTitleId = createIdea(authorId2, NO_TITLE, "Without a lot of information I will lost an idea");
        IdeaId noDescriptionId = createIdea(authorId3, "Create a project", NO_DESCRIPTION);

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

    @Test
    void shouldUpdateIdeasCollaborators() {
        AuthorId authorId = randomAuthorId();
        CollaboratorId collaboratorId1 = givenExistingCollaborator();
        CollaboratorId collaboratorId2 = givenExistingCollaborator();
        CollaboratorId collaboratorId3 = givenExistingCollaborator();
        CollaboratorId collaboratorId4 = givenExistingCollaborator();
        IdeaId ideaId1 = createIdea(authorId, "Idea One");
        IdeaId ideaId2 = createIdea(authorId, "Idea Two");
        IdeaId ideaId3 = createIdea(authorId, "Idea Three");

        updateIdeaCollaborators(authorId, ideaId1, collaboratorId1, collaboratorId2, collaboratorId3);
        updateIdeaCollaborators(authorId, ideaId3, collaboratorId3, collaboratorId4);

        assertThat(findBy(authorId, ideaId1))
                .hasCollaborators(collaboratorId1, collaboratorId2, collaboratorId3);
        assertThat(findBy(authorId, ideaId2)).hasNoCollaborators();
        assertThat(findBy(authorId, ideaId3))
                .hasCollaborators(collaboratorId3, collaboratorId4);
    }

    private void updateIdeaCollaborators(AuthorId authorId, IdeaId ideaId, CollaboratorId... collaborators) {
        Idea actual = findBy(authorId, ideaId);
        stream(collaborators).forEach(collaboratorId -> {
            actual.share(collaboratorRepository, collaboratorId);
        });
        ideaRepository.save(actual);
    }

    private CollaboratorId givenExistingCollaborator() {
        String name = randomString();
        String password = randomString();
        UUID id = userTestRepository.save(UserTestFactory.user(name, password));
        collaboratorIds.add(id);

        return CollaboratorId.from(id);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }

    private AuthorId randomAuthorId() {
        return AuthorId.from(UUID.randomUUID());
    }

    private IdeaId createIdea(AuthorId authorId, String title) {
        return createIdea(authorId, title, NO_DESCRIPTION);
    }

    private IdeaId createIdea(AuthorId authorId, String title, String description) {
        Idea idea = factory.create(authorId.value(), title, description);
        IdeaId id = ideaRepository.save(idea);
        ideaIds.add(id);

        return id;
    }

    private Idea findBy(AuthorId authorId, IdeaId id) {
        return ideaRepository.findBy(authorId, id);
    }
}