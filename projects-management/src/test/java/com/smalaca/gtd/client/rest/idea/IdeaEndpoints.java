package com.smalaca.gtd.client.rest.idea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalaca.gtd.client.rest.response.WebResponse;
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

    public IdeaEndpoints(MockMvc mockMvc, ObjectMapper objectMapper, HttpStatus expectedHttpStatus) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public WebResponse create(IdeaTestDto.IdeaTestDtoBuilder idea) {
        return execute(post(asJsonIdea(idea)));
    }

    private String asJsonIdea(IdeaTestDto.IdeaTestDtoBuilder idea) {
        try {
            return objectMapper.writeValueAsString(idea.build());
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private MockHttpServletRequestBuilder post(String content) {
        return MockMvcRequestBuilders.post(IDEA_URL)
                .with(csrf())
                .content(content)
                .contentType(APPLICATION_JSON);
    }

    public WebResponse findBy(UUID id) {
        return execute(get(IDEA_URL + "/" + id));
    }

    public WebResponse findAll() {
        return execute(get(IDEA_URL));
    }

    private WebResponse execute(MockHttpServletRequestBuilder request) {
        try {
            MockHttpServletResponse response = mockMvc.perform(request)
                    .andExpect(status().is(expectedHttpStatus.value()))
                    .andReturn()
                    .getResponse();

            return new WebResponse(objectMapper, response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
