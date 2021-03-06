package com.smalaca.gtd.shared.configuration.api.web.rest.validationerror;

import com.smalaca.gtd.shared.libraries.validation.api.web.rest.ValidationErrorsDto;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.ArrayUtils.isArrayIndexValid;

class ValidationErrorsDtoBuilder {
    private final ValidationErrorsDto.Builder builder = ValidationErrorsDto.validationErrorsDto();

    ValidationErrorsDto build() {
        return builder.build();
    }

    void add(ObjectError error) {
        Optional<List<String>> fields = fieldsFrom(error);

        if (fields.isPresent()) {
            builder.withFieldError(fields.get(), error.getDefaultMessage());
        } else {
            throw new MissingValidationArgumentException(error);
        }
    }

    private static Optional<List<String>> fieldsFrom(ObjectError error) {
        if (error instanceof FieldError) {
            return fieldsFromFieldError((FieldError) error);
        } else {
            return fieldsFromObjectError(error);
        }
    }

    private static Optional<List<String>> fieldsFromFieldError(FieldError error) {
        return Optional.of(asList(error.getField()));
    }

    private static Optional<List<String>> fieldsFromObjectError(ObjectError error) {
        Object[] arguments = error.getArguments();

        if (arguments != null && isArrayIndexValid(arguments, 1) && arguments[1] instanceof String[]) {
            return Optional.of(asStringsList(arguments[1]));
        } else {
            return Optional.empty();
        }
    }

    private static List<String> asStringsList(Object argument) {
        return asList((String[]) argument);
    }
}
