package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.client.rest.ProjectsManagementClient;
import com.smalaca.gtd.client.rest.idea.CreateIdeaTestCommand;
import com.smalaca.gtd.client.rest.idea.IdeaTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDto;
import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestBuilder;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenCollaborators;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenIdeas;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenProjectManagementTestConfiguration;
import com.smalaca.gtd.tests.annotation.RestControllerTest;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.client.rest.RestClientResponseAssertions.assertThat;
import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestBuilder.idea;
import static com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder.user;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerTest
@WithMockUser("USER")
@Import(GivenProjectManagementTestConfiguration.class)
class IdeaRestControllerSystemTest {
    @Autowired private GivenIdeas givenIdeas;
    @Autowired private GivenUsers givenUsers;
    @Autowired private GivenCollaborators givenCollaborators;
    @Autowired private ProjectsManagementClient client;
    private UUID userId;

    @BeforeEach
    void givenIdeas() {
        userId = givenUsers.existing(user("USER"));
        givenIdeas.existing(ideaForUser().title("IdeaOne").description("With description"));
        givenIdeas.existing(ideaForUser().title("IdeaTwo"));
        givenIdeas.existing(ideaForUser().description("Description is everything"));
        givenIdeas.existing(ideaForUser().title("Idea Four").description("The greatest ideas makes us better!"));
    }

    @AfterEach
    void deleteCreatedEntities() {
        givenUsers.deleteAll();
        givenIdeas.deleteAll();
        givenCollaborators.deleteAll();
    }

    @Test
    void shouldFindNoIdeaForGivenId() {
        client.idea(NOT_FOUND).findBy(notExisting());
    }

    @Test
    void shouldFindCreatedIdea() {
        CreateIdeaTestCommand.CreateIdeaTestCommandBuilder idea = CreateIdeaTestCommand.builder()
                .title("I have an idea")
                .description("And the idea is really good");

        UUID id = createIdea(idea);

        assertThat(findBy(id))
                .hasTitle("I have an idea")
                .hasDescription("And the idea is really good")
                .hasNoCollaborators();
    }

    private UUID createIdea(CreateIdeaTestCommand.CreateIdeaTestCommandBuilder idea) {
        UUID id = client.idea(CREATED).create(idea).asUuid();
        givenIdeas.existing(IdeaId.from(id));

        return id;
    }

    @Test
    void shouldNotCreateIdea() {
        ValidationErrorsTestDto actual = client.idea().create(CreateIdeaTestCommand.builder()).asValidationErrors();

        assertThat(actual)
                .hasOneError()
                .hasErrorThat(error -> assertThat(error)
                        .hasFields("title", "description")
                        .hasMessage("Title or description cannot be empty."));
    }

    @Test
    void shouldFindAllIdeas() {
        List<IdeaTestDto> actual = client.idea().findAll().asIdeas();

        Assertions.assertThat(actual)
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
    void shouldShareIdeaWithCollaborators() {
        UUID peterParkerId = givenCollaborators.existing("peter parker").value();
        givenCollaborators.existing("natasha romanoff");
        givenCollaborators.existing("steve rogers");
        UUID wandaMaximoffId = givenCollaborators.existing("wanda maximoff").value();
        UUID ideaId = givenIdeas.existing(ideaForUser().title("Great idea").description("So Good")).value();

        client.idea().share(ideaId, peterParkerId);
        client.idea().share(ideaId, wandaMaximoffId);

        assertThat(findBy(ideaId))
                .hasTitle("Great idea")
                .hasDescription("So Good")
                .hasCollaborators(2)
                .hasCollaborator(peterParkerId, "peter parker")
                .hasCollaborator(wandaMaximoffId, "wanda maximoff");
    }

    @Test
    void shouldShareNotExistingIdeaWithCollaborators() {
        UUID peterParkerId = givenCollaborators.existing("peter parker").value();

        client.idea(NOT_FOUND).share(notExisting(), peterParkerId);
    }

    @Test
    void shouldShareIdeaWithNotExistingCollaborator() {
        UUID ideaId = givenIdeas.existing(ideaForUser().title("Amazing idea")).value();

        client.idea(NOT_FOUND).share(ideaId, notExisting());

        assertThat(findBy(ideaId))
                .hasTitle("Amazing idea")
                .hasNoCollaborators();
    }

    private UUID notExisting() {
        return UUID.randomUUID();
    }

    private IdeaTestBuilder ideaForUser() {
        return idea(AuthorId.from(userId));
    }

    private IdeaTestDto findBy(UUID id) {
        return client.idea().findBy(id).asIdea();
    }
}
