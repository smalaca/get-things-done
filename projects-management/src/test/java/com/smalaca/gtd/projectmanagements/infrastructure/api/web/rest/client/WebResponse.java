package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class WebResponse {
    private final MockHttpServletResponse response;
    private final ObjectMapper objectMapper;

    WebResponse(ObjectMapper objectMapper, MockHttpServletResponse response) {
        this.response = response;
        this.objectMapper = objectMapper;
    }

    public UUID asUuid() {
        return UUID.fromString(asString().replace("\"",""));
    }

    ValidationErrorsDto asValidationResponse() {
        try {
            return objectMapper.readValue(asString(), ValidationErrorsDto.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String asString() {
        try {
            return response.getContentAsString();
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
