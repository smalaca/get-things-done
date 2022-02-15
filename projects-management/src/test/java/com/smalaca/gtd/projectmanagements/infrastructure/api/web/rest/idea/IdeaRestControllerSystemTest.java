package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.IdeaTestDto;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.IdeaTestDtoAssertion;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ProjectsManagementClient;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ValidationErrorsTestDto;
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
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        IdeaTestDto.IdeaTestDtoBuilder idea = IdeaTestDto.builder()
                .title("I have an idea")
                .description("And the idea is really good");

        UUID id = createIdea(idea);

        assertThat(repository.findById(id))
                .hasTitle("I have an idea")
                .hasDescription("And the idea is really good");
    }

    @Test
    void shouldNotCreateIdea() {
        ValidationErrorsTestDto actual = client.idea().create(IdeaTestDto.builder()).asValidationErrors();

        assertThat(actual)
                .hasOneError()
                .hasErrorThat(error -> assertThat(error)
                        .hasFields("title", "description")
                        .hasMessage("Title or description cannot be empty."));
    }

    @Test
    void shouldFindAllIdeas() {
        givenIdeas();

        List<IdeaTestDto> actual = client.idea().findAll().asIdeas();

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
        UUID id = createIdea(IdeaTestDto.builder().title("Idea Five").description("It would be good to do something good"));

        IdeaTestDto actual = client.idea().findBy(id).asIdea();

        IdeaTestDtoAssertion.assertThat(actual)
                .hasTitle("Idea Five")
                .hasDescription("It would be good to do something good");
    }

    @Test
    void shouldFindNoIdea() {
        givenIdeas();

        client.idea(NOT_FOUND).findBy(UUID.randomUUID());
    }

    private void givenIdeas() {
        createIdea(IdeaTestDto.builder().title("IdeaOne").description("With description"));
        createIdea(IdeaTestDto.builder().title("IdeaTwo"));
        createIdea(IdeaTestDto.builder().description("Description is everything"));
        createIdea(IdeaTestDto.builder().title("Idea Four").description("The greatest ideas makes us better!"));
    }

    private UUID createIdea(IdeaTestDto.IdeaTestDtoBuilder idea) {
        UUID id = client.idea(CREATED).create(idea).asUuid();
        ids.add(id);
        return id;
    }
}
