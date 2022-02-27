package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.shared.validation.controller.ValidationErrorsDto;
import com.smalaca.gtd.shared.validation.controller.ValidationFieldErrorDto;
import com.smalaca.gtd.usermanagement.domain.user.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ValidationErrorsDto> handle(UserAlreadyExistsException exception) {
        ValidationFieldErrorDto error = new ValidationFieldErrorDto(
                List.of("userName"), exception.getMessage());

        return ResponseEntity.ok(new ValidationErrorsDto(List.of(error)));
    }
}
