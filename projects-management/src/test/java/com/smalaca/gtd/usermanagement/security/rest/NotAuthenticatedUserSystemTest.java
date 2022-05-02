package com.smalaca.gtd.usermanagement.security.rest;

import com.smalaca.gtd.client.rest.ProjectsManagementClient;
import com.smalaca.gtd.client.rest.idea.CreateIdeaTestCommand;
import com.smalaca.gtd.tests.annotation.RestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerTest
class NotAuthenticatedUserSystemTest {
    @Autowired
    private ProjectsManagementClient client;

    @Test
    void shouldBeForbiddenToCreateIdeaForNotAuthenticatedUser() {
        CreateIdeaTestCommand.CreateIdeaTestCommandBuilder idea = CreateIdeaTestCommand.builder()
                .title("Idea")
                .description("With description");

        client.idea(UNAUTHORIZED).create(idea);
    }
}
