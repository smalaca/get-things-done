package com.smalaca.gtd.shared.libraries.validation.api.web.rest;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsDto {
    private final List<ValidationFieldErrorDto> errors;

    public ValidationErrorsDto(List<ValidationFieldErrorDto> errors) {
        this.errors = errors;
    }
}
