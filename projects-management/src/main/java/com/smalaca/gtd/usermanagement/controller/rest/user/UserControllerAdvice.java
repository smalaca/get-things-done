package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.shared.libraries.validation.api.web.rest.ValidationErrorsDto;
import com.smalaca.gtd.usermanagement.domain.user.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.smalaca.gtd.shared.libraries.validation.api.web.rest.ValidationErrorsDto.validationErrorsDto;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ValidationErrorsDto> handle(UserAlreadyExistsException exception) {
        ValidationErrorsDto dto = validationErrorsDto()
                .withFieldError("userName", exception.getMessage())
                .build();

        return ResponseEntity.ok(dto);
    }
}
