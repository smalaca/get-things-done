package com.smalaca.gtd.shared.configuration.api.web.rest.validationerror;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsDto {
    private final List<ValidationFieldErrorDto> errors;

    public ValidationErrorsDto(List<ValidationFieldErrorDto> errors) {
        this.errors = errors;
    }
}
