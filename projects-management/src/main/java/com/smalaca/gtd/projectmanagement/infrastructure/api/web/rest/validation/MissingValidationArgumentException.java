package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.validation;

import org.springframework.validation.ObjectError;

class MissingValidationArgumentException extends RuntimeException {
    MissingValidationArgumentException(ObjectError error) {
        super(error.toString());
    }
}
