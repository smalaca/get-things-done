package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.validation;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

@Getter
public final class ValidationFieldErrorDto {
    private final List<String> fields;
    private final String message;

    private ValidationFieldErrorDto(List<String> fields, String message) {
        this.fields = fields;
        this.message = message;
    }

    static ValidationFieldErrorDto create(ObjectError error) {
        String[] fields = (String[]) error.getArguments()[1];
        return new ValidationFieldErrorDto(Arrays.asList(fields), error.getDefaultMessage());
    }
}
