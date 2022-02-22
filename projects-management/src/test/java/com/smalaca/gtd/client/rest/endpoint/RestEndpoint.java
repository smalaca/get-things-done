package com.smalaca.gtd.client.rest.endpoint;

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

public class RestEndpoint {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final HttpStatus expectedHttpStatus;

    public RestEndpoint(MockMvc mockMvc, ObjectMapper objectMapper, HttpStatus expectedHttpStatus) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public WebResponse post(String url, Object dto) {
        return execute(postRequest(url, asJson(dto)));
    }

    private String asJson(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private MockHttpServletRequestBuilder postRequest(String url, String content) {
        return MockMvcRequestBuilders.post(url)
                .with(csrf())
                .content(content)
                .contentType(APPLICATION_JSON);
    }

    public WebResponse get(String urlTemplate) {
        return execute(MockMvcRequestBuilders.get(urlTemplate));
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
