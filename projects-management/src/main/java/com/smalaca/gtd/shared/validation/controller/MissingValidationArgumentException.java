package com.smalaca.gtd.shared.validation.controller;

import org.springframework.validation.ObjectError;

class MissingValidationArgumentException extends RuntimeException {
    MissingValidationArgumentException(ObjectError error) {
        super(error.toString());
    }
}
