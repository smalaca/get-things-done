package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ProjectsManagementClient;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ValidationErrorsDto;
import com.smalaca.gtd.projectmanagements.infrastructure.repository.jpa.idea.IdeaTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

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
    private UUID id;

    @AfterEach
    void deleteCreatedIdea() {
        if (id != null) {
            repository.deleteById(id);
        }
    }

    @Test
    void shouldCreateIdea() {
        String title = "I have an idea";
        String description = "And the idea is really good";

        id = client.createIdea(title, description);

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
}
