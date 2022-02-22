package com.smalaca.gtd.shared.validation.controller;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsDto {
    private final List<ValidationFieldErrorDto> errors;

    ValidationErrorsDto(List<ValidationFieldErrorDto> errors) {
        this.errors = errors;
    }
}
