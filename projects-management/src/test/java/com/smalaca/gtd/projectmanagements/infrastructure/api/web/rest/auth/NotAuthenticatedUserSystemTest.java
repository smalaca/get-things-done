package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.auth;

import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.IdeaTestDto;
import com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client.ProjectsManagementClient;
import com.smalaca.gtd.tests.annotation.SystemTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@SpringBootTest
@Import({ProjectsManagementClient.class})
@AutoConfigureMockMvc
@SystemTest
class NotAuthenticatedUserSystemTest {
    @Autowired
    private ProjectsManagementClient client;

    @Test
    void shouldBeForbiddenToCreateIdeaForNotAuthenticatedUser() {
        IdeaTestDto.IdeaTestDtoBuilder idea = IdeaTestDto.builder()
                .title("Idea")
                .description("With description");

        client.idea(UNAUTHORIZED).create(idea);
    }
}
