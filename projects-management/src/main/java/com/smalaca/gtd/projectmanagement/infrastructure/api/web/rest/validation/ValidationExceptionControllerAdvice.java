package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class ValidationExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ValidationErrorsDto> handle(MethodArgumentNotValidException exception) {
        List<ValidationFieldErrorDto> fieldErrors = exception.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(ValidationFieldErrorDto::create)
                .collect(toList());

        return ResponseEntity.ok(new ValidationErrorsDto(fieldErrors));
    }
}
