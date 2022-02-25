package com.smalaca.gtd.client.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalaca.gtd.client.rest.idea.IdeaEndpoints;
import com.smalaca.gtd.client.rest.user.UserEndpoints;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpStatus.CREATED;
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
        return IdeaEndpoints.create(mockMvc, objectMapper, expectedHttpStatus);
    }

    public UserEndpoints user() {
        return user(CREATED);
    }

    public UserEndpoints user(HttpStatus expectedHttpStatus) {
        return UserEndpoints.create(mockMvc, objectMapper, expectedHttpStatus);
    }
}
