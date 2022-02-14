package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.validation;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsDto {
    private final List<ValidationFieldErrorDto> errors;

    ValidationErrorsDto(List<ValidationFieldErrorDto> errors) {
        this.errors = errors;
    }
}
