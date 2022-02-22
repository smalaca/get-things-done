package com.smalaca.gtd.client.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalaca.gtd.client.rest.endpoint.RestEndpoint;
import com.smalaca.gtd.client.rest.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

public class UserEndpoints {
    private static final String USER_URL = "/user";
    private final RestEndpoint endpoint;

    private UserEndpoints(RestEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public static UserEndpoints create(MockMvc mockMvc, ObjectMapper objectMapper, HttpStatus expectedHttpStatus) {
        return new UserEndpoints(new RestEndpoint(mockMvc, objectMapper, expectedHttpStatus));
    }

    public WebResponse create(UserTestDto.UserTestDtoBuilder user) {
        return endpoint.post(USER_URL, user.build());
    }
}
