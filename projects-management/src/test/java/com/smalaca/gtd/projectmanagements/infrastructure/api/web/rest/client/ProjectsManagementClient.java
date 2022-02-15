package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpStatus.OK;

public class ProjectsManagementClient {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    ProjectsManagementClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public IdeaEndpoints idea() {
        return idea(OK);
    }

    public IdeaEndpoints idea(HttpStatus expectedHttpStatus) {
        return new IdeaEndpoints(mockMvc, objectMapper, expectedHttpStatus);
    }
}
