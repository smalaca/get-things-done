package com.smalaca.gtd.shared.configuration.api.web.rest.validationerror;

import com.smalaca.gtd.shared.libraries.validation.api.web.rest.ValidationErrorsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ValidationErrorsDto> handle(MethodArgumentNotValidException exception) {
        ValidationErrorsDtoBuilder builder = new ValidationErrorsDtoBuilder();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(builder::add);

        return ResponseEntity.ok(builder.build());
    }
}
