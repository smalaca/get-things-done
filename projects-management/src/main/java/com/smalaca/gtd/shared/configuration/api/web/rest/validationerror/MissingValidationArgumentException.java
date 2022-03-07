package com.smalaca.gtd.shared.configuration.api.web.rest.validationerror;

import org.springframework.validation.ObjectError;

class MissingValidationArgumentException extends RuntimeException {
    MissingValidationArgumentException(ObjectError error) {
        super(error.toString());
    }
}
