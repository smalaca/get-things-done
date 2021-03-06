package com.smalaca.gtd.client.rest.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.smalaca.gtd.client.rest.idea.IdeaTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDto;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public class WebResponse {
    private final MockHttpServletResponse response;
    private final ObjectMapper objectMapper;

    public WebResponse(ObjectMapper objectMapper, MockHttpServletResponse response) {
        this.response = response;
        this.objectMapper = objectMapper;
    }

    public UUID asUuid() {
        return UUID.fromString(asString());
    }

    public ValidationErrorsTestDto asValidationErrors() {
        return as(ValidationErrorsTestDto.class);
    }

    public List<IdeaTestDto> asIdeas() {
        try {
            IdeaTestDto[] response = objectMapper.readValue(asString(), IdeaTestDto[].class);
            return Lists.newArrayList(response);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public IdeaTestDto asIdea() {
        return as(IdeaTestDto.class);
    }

    private  <T> T as(Class<T> valueType) {
        try {
            return objectMapper.readValue(asString(), valueType);
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
