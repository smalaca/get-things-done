package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectsManagementClient {
    private static final String IDEA_URL = "/idea";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    ProjectsManagementClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ValidationErrorsDto tryToCreateIdea(String title, String description) {
        return createIdea(title, description, OK).asValidationResponse();
    }

    public UUID createIdea(String title, String description) {
        return createIdea(title, description, CREATED).asUuid();
    }

    private WebResponse createIdea(String title, String description, HttpStatus expectedHttpStatus) {
        try {
            MockHttpServletResponse response = mockMvc.perform(post(IDEA_URL, asJsonIdea(title, description)))
                    .andExpect(status().is(expectedHttpStatus.value()))
                    .andReturn()
                    .getResponse();

            return new WebResponse(objectMapper, response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private String asJsonIdea(String title, String description) {
        return "{\"title\":\"" + title + "\",\"description\":\"" + description + "\"}";
    }

    private MockHttpServletRequestBuilder post(String url, String content) {
        return MockMvcRequestBuilders.post(url)
                .with(csrf())
                .content(content)
                .contentType(APPLICATION_JSON);
    }
}
