package com.smalaca.gtd.client.rest.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalaca.gtd.client.rest.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserEndpoints {
    private static final String USER_URL = "/user";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final HttpStatus expectedHttpStatus;

    public UserEndpoints(MockMvc mockMvc, ObjectMapper objectMapper, HttpStatus expectedHttpStatus) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public WebResponse create(UserTestDto.UserTestDtoBuilder user) {
        return execute(post(asJsonUser(user)));
    }

    private String asJsonUser(UserTestDto.UserTestDtoBuilder user) {
        try {
            return objectMapper.writeValueAsString(user.build());
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private MockHttpServletRequestBuilder post(String content) {
        return MockMvcRequestBuilders.post(USER_URL)
                .with(csrf())
                .content(content)
                .contentType(APPLICATION_JSON);
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
