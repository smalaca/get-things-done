package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsTestDto {
    private List<ValidationFieldErrorTestDto> errors;
}
