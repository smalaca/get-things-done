package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectsManagementClient {
    private static final String IDEA_URL = "/idea";

    private final MockMvc mockMvc;

    ProjectsManagementClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public UUID createIdea(String title, String description) {
        try {
            String response = mockMvc.perform(post(IDEA_URL, asJsonIdea(title, description)))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse().getContentAsString();

            return asUuid(response);
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

    private UUID asUuid(String response) {
        return UUID.fromString(response.replace("\"",""));
    }
}
