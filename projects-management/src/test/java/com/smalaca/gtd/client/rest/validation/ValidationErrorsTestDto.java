package com.smalaca.gtd.client.rest.validation;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsTestDto {
    private List<ValidationFieldErrorTestDto> errors;
}
