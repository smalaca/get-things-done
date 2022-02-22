package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.validation;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.ArrayUtils.isArrayIndexValid;

@Getter
public final class ValidationFieldErrorDto {
    private final List<String> fields;
    private final String message;

    private ValidationFieldErrorDto(List<String> fields, String message) {
        this.fields = fields;
        this.message = message;
    }

    static ValidationFieldErrorDto create(ObjectError error) {
        Optional<List<String>> fields = fieldsFrom(error);

        if (fields.isPresent()) {
            return new ValidationFieldErrorDto(fields.get(), error.getDefaultMessage());
        } else {
            throw new MissingValidationArgumentException(error);
        }
    }

    private static Optional<List<String>> fieldsFrom(ObjectError error) {
        Object[] arguments = error.getArguments();

        if (arguments != null && isArrayIndexValid(arguments, 1) && arguments[1] instanceof String[]) {
            return Optional.of(asList(arguments[1]));
        } else {
            return Optional.empty();
        }
    }

    private static List<String> asList(Object argument) {
        return Arrays.asList((String[]) argument);
    }
}
