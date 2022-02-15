package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectsManagementClient {
    private static final String IDEA_URL = "/idea";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    ProjectsManagementClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ValidationErrorsDto tryToCreateIdea(IdeaTestDto.IdeaTestDtoBuilder idea) {
        return createIdea(OK, idea).asValidationResponse();
    }

    public UUID createIdea(IdeaTestDto.IdeaTestDtoBuilder idea) {
        return createIdea(CREATED, idea).asUuid();
    }

    private WebResponse createIdea(HttpStatus expectedHttpStatus, IdeaTestDto.IdeaTestDtoBuilder idea) {
        try {
            MockHttpServletResponse response = mockMvc.perform(post(IDEA_URL, asJsonIdea(idea)))
                    .andExpect(status().is(expectedHttpStatus.value()))
                    .andReturn()
                    .getResponse();

            return webResponseOf(response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private String asJsonIdea(IdeaTestDto.IdeaTestDtoBuilder idea) {
        try {
            return objectMapper.writeValueAsString(idea.build());
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<IdeaTestDto> findAllIdeas() {
        try {
            MockHttpServletResponse response = mockMvc.perform(get(IDEA_URL))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse();
            return webResponseOf(response).asIdeas();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void tryToFindIdeaById(UUID id) {
        try {
            mockMvc.perform(get(IDEA_URL + "/" + id))
                    .andExpect(status().isNotFound());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public IdeaTestDto findIdeaById(UUID id) {
        try {
            MockHttpServletResponse response = mockMvc.perform(get(IDEA_URL + "/" + id))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse();
            return webResponseOf(response).asIdea();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private WebResponse webResponseOf(MockHttpServletResponse response) {
        return new WebResponse(objectMapper, response);
    }

    private MockHttpServletRequestBuilder post(String url, String content) {
        return MockMvcRequestBuilders.post(url)
                .with(csrf())
                .content(content)
                .contentType(APPLICATION_JSON);
    }
}
