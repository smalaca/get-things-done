package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.validation;

import org.springframework.validation.ObjectError;

class MissingValidationArgumentException extends RuntimeException {
    MissingValidationArgumentException(ObjectError error) {
        super(error.toString());
    }
}
