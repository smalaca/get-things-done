package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.client.rest.ProjectsManagementClient;
import com.smalaca.gtd.client.rest.idea.IdeaTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDto;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaTestRepository;
import com.smalaca.gtd.tests.annotation.SystemTest;
import com.smalaca.gtd.usermanagement.domain.user.UserTestFactory;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.client.rest.RestClientResponseAssertions.assertThat;
import static com.smalaca.gtd.projectmanagement.domain.idea.IdeaIdTestFactory.ideaIdFrom;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
@Import({IdeaTestRepository.class, UserTestRepository.class, ProjectsManagementClient.class})
@AutoConfigureMockMvc
@WithMockUser("USER")
@SystemTest
class IdeaRestControllerSystemTest {
    @Autowired private IdeaTestRepository ideaTestRepository;
    @Autowired private UserTestRepository userTestRepository;
    @Autowired private ProjectsManagementClient client;
    private final List<IdeaId> ids = new ArrayList<>();
    private UUID userId;

    @BeforeEach
    void givenIdeas() {
        userId = userTestRepository.save(UserTestFactory.user("USER", UUID.randomUUID().toString()));
        createIdea(IdeaTestDto.builder().title("IdeaOne").description("With description"));
        createIdea(IdeaTestDto.builder().title("IdeaTwo"));
        createIdea(IdeaTestDto.builder().description("Description is everything"));
        createIdea(IdeaTestDto.builder().title("Idea Four").description("The greatest ideas makes us better!"));
    }

    @AfterEach
    void deleteCreatedIdea() {
        ids.forEach(ideaTestRepository::deleteById);
        userTestRepository.deleteBy(userId);
    }

    @Test
    void shouldFindNoIdeaForGivenId() {
        client.idea(NOT_FOUND).findBy(UUID.randomUUID());
    }

    @Test
    void shouldFindCreatedIdea() {
        IdeaTestDto.IdeaTestDtoBuilder idea = IdeaTestDto.builder()
                .title("I have an idea")
                .description("And the idea is really good");

        UUID id = createIdea(idea);

        IdeaTestDto created = client.idea().findBy(id).asIdea();
        assertThat(created)
                .hasTitle("I have an idea")
                .hasDescription("And the idea is really good");
    }

    private UUID createIdea(IdeaTestDto.IdeaTestDtoBuilder idea) {
        UUID id = client.idea(CREATED).create(idea).asUuid();
        ids.add(ideaIdFrom(id));
        return id;
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
}
