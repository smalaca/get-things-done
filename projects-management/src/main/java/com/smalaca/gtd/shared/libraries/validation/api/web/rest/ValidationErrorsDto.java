package com.smalaca.gtd.shared.libraries.validation.api.web.rest;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorsDto {
    private final List<ValidationFieldErrorDto> errors;

    public ValidationErrorsDto(List<ValidationFieldErrorDto> errors) {
        this.errors = errors;
    }

    public static Builder validationErrorsDto() {
        return new Builder();
    }

    public static class Builder {
        private final List<ValidationFieldErrorDto> errors = new ArrayList<>();

        public Builder withFieldError(String fieldName, String errorMessage) {
            return withFieldError(List.of(fieldName), errorMessage);
        }

        public Builder withFieldError(List<String> fieldNames, String errorMessage) {
            errors.add(new ValidationFieldErrorDto(fieldNames, errorMessage));
            return this;
        }

        public ValidationErrorsDto build() {
            return new ValidationErrorsDto(errors);
        }
    }
}
