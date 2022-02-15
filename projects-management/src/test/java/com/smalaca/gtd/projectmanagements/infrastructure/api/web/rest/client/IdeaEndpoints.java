package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IdeaEndpoints {
    private static final String IDEA_URL = "/idea";
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final HttpStatus expectedHttpStatus;

    IdeaEndpoints(MockMvc mockMvc, ObjectMapper objectMapper, HttpStatus expectedHttpStatus) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public WebResponse create(IdeaTestDto.IdeaTestDtoBuilder idea) {
        try {
            MockHttpServletResponse response = mockMvc.perform(post(asJsonIdea(idea)))
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

    private WebResponse webResponseOf(MockHttpServletResponse response) {
        return new WebResponse(objectMapper, response);
    }

    private MockHttpServletRequestBuilder post(String content) {
        return MockMvcRequestBuilders.post(IDEA_URL)
                .with(csrf())
                .content(content)
                .contentType(APPLICATION_JSON);
    }

    public WebResponse findBy(UUID id) {
        try {
            MockHttpServletResponse response = mockMvc.perform(get(IDEA_URL + "/" + id))
                    .andExpect(status().is(expectedHttpStatus.value()))
                    .andReturn()
                    .getResponse();

            return webResponseOf(response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public WebResponse findAll() {
        try {
            MockHttpServletResponse response = mockMvc.perform(get(IDEA_URL))
                    .andExpect(status().is(expectedHttpStatus.value()))
                    .andReturn()
                    .getResponse();
            return webResponseOf(response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
