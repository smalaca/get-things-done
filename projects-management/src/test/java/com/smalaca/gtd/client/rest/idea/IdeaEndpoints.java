package com.smalaca.gtd.client.rest.idea;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalaca.gtd.client.rest.endpoint.RestEndpoint;
import com.smalaca.gtd.client.rest.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

public class IdeaEndpoints {
    private static final String IDEA_URL = "/idea";
    private final RestEndpoint endpoint;

    private IdeaEndpoints(RestEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public static IdeaEndpoints create(MockMvc mockMvc, ObjectMapper objectMapper, HttpStatus expectedHttpStatus) {
        return new IdeaEndpoints(new RestEndpoint(mockMvc, objectMapper, expectedHttpStatus));
    }

    public WebResponse create(IdeaTestDto.IdeaTestDtoBuilder idea) {
        return endpoint.post(IDEA_URL, idea.build());
    }

    public WebResponse findBy(UUID id) {
        return endpoint.get(ideaUrl(id));
    }

    public WebResponse findAll() {
        return endpoint.get(IDEA_URL);
    }

    public void share(UUID ideaId, UUID collaboratorId) {
        endpoint.patch(ideaUrl(ideaId), new IdeaShareTestDto(collaboratorId));
    }

    private String ideaUrl(UUID ideaId) {
        return IDEA_URL + "/" + ideaId;
    }
}
