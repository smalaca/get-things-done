package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.IdeaTestDto;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.IdeaTestDtoAssertion;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ProjectsManagementClient;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ValidationErrorsDto;
import com.smalaca.gtd.projectmanagements.infrastructure.repository.jpa.idea.IdeaTestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagements.domain.idea.IdeaAssertion.assertThat;
import static com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ResponseAssertions.assertThat;

@SpringBootTest
@Import({IdeaTestRepository.class, ProjectsManagementClient.class})
@AutoConfigureMockMvc
@WithMockUser("USER")
@Tag("SystemTest")
class IdeaRestControllerSystemTest {
    @Autowired
    private IdeaTestRepository repository;

    @Autowired
    private ProjectsManagementClient client;
    private final List<UUID> ids = new ArrayList<>();

    @AfterEach
    void deleteCreatedIdea() {
        ids.forEach(repository::deleteById);
    }

    @Test
    void shouldCreateIdea() {
        String title = "I have an idea";
        String description = "And the idea is really good";

        UUID id = createIdea(title, description);

        assertThat(repository.findById(id))
                .hasTitle(title)
                .hasDescription(description);
    }

    @Test
    void shouldNotCreateIdea() {
        String title = "";
        String description = "";

        ValidationErrorsDto actual = client.tryToCreateIdea(title, description);

        assertThat(actual)
                .hasOneError()
                .hasErrorThat(error -> assertThat(error)
                        .hasFields("title", "description")
                        .hasMessage("Title or description cannot be empty."));
    }

    @Test
    void shouldFindAllIdeas() {
        givenIdeas();

        List<IdeaTestDto> actual = client.findAllIdeas();

        Assertions.assertThat(actual)
                .hasSize(4)
                .anySatisfy(idea -> IdeaTestDtoAssertion.assertThat(idea)
                        .hasTitle("IdeaOne")
                        .hasDescription("With description"))
                .anySatisfy(idea -> IdeaTestDtoAssertion.assertThat(idea)
                        .hasTitle("IdeaTwo")
                        .hasNoDescription())
                .anySatisfy(idea -> IdeaTestDtoAssertion.assertThat(idea)
                        .hasNoTitle()
                        .hasDescription("Description is everything"))
                .anySatisfy(idea -> IdeaTestDtoAssertion.assertThat(idea)
                        .hasTitle("Idea Four")
                        .hasDescription("The greatest ideas makes us better!"));
    }

    @Test
    void shouldFindSpecificIdea() {
        givenIdeas();
        UUID id = createIdea("Idea Five", "It would be good to do something good");

        IdeaTestDto actual = client.findIdeaById(id);

        IdeaTestDtoAssertion.assertThat(actual)
                .hasTitle("Idea Five")
                .hasDescription("It would be good to do something good");
    }

    @Test
    void shouldFindNoIdea() {
        givenIdeas();

        client.tryToFindIdeaById(UUID.randomUUID());
    }

    private void givenIdeas() {
        createIdea("IdeaOne", "With description");
        createIdea("IdeaTwo", null);
        createIdea(null, "Description is everything");
        createIdea("Idea Four", "The greatest ideas makes us better!");
    }

    private UUID createIdea(String title, String description) {
        UUID id = client.createIdea(title, description);
        ids.add(id);
        return id;
    }
}
