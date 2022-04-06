package com.smalaca.gtd.projectmanagement.query.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenCollaborators;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenIdeas;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenProjectManagementTestConfiguration;
import com.smalaca.gtd.tests.annotation.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestBuilder.idea;
import static com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModelAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import({IdeaQueryService.class, GivenProjectManagementTestConfiguration.class})
class IdeaQueryServiceIntegrationTest {
    private static final AuthorId AUTHOR_ID = AuthorId.from(UUID.randomUUID());

    @Autowired private GivenIdeas givenIdeas;
    @Autowired private GivenCollaborators givenCollaborators;
    @Autowired private IdeaQueryService service;

    @AfterEach
    void removeIdeas() {
        givenIdeas.deleteAll();
    }

    @Test
    void shouldFindNothingWhenNoIdeas() {
        List<IdeaReadModel> actual = service.findAll();

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFindAllIdeas() {
        givenIdeas();

        List<IdeaReadModel> actual = service.findAll();

        assertThat(actual)
                .hasSize(4)
                .anySatisfy(idea -> assertThat(idea)
                        .hasTitle("IdeaOne")
                        .hasDescription("With description"))
                .anySatisfy(idea -> assertThat(idea)
                        .hasTitle("IdeaTwo")
                        .hasNoDescription())
                .anySatisfy(idea -> assertThat(idea)
                        .hasNoTitle()
                        .hasDescription("Description is everything"))
                .anySatisfy(idea -> assertThat(idea)
                        .hasTitle("Idea Four")
                        .hasDescription("The greatest ideas makes us better!"));
    }

    @Test
    void shouldFindNothingWhenIdeaWithGivenIdDoesNotExist() {
        givenIdeas();

        Optional<IdeaReadModel> actual = service.findById(UUID.randomUUID());

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFindSpecificIdea() {
        CollaboratorId peterParkerId = givenCollaborators.existing("peter parker");
        givenCollaborators.existing("natasha romanoff");
        givenCollaborators.existing("steve rogers");
        CollaboratorId wandaMaximoffId = givenCollaborators.existing("wanda maximoff");
        givenIdeas();
        IdeaId id = givenIdeas.existing(idea(AUTHOR_ID)
                .title("Idea Five")
                .description("It would be good to do something good")
                .collaborators(peterParkerId, wandaMaximoffId));

        IdeaReadModel actual = service.findById(id.value()).get();

        assertThat(actual)
                .hasTitle("Idea Five")
                .hasDescription("It would be good to do something good")
                .hasCollaborators(2)
                .hasCollaborator(peterParkerId, "peter parker")
                .hasCollaborator(wandaMaximoffId, "wanda maximoff");
    }

    private void givenIdeas() {
        givenIdeas.existing(idea(AUTHOR_ID).title("IdeaOne").description("With description"));
        givenIdeas.existing(idea(AUTHOR_ID).title("IdeaTwo"));
        givenIdeas.existing(idea(AUTHOR_ID).description("Description is everything"));
        givenIdeas.existing(idea(AUTHOR_ID).title("Idea Four").description("The greatest ideas makes us better!"));
    }
}