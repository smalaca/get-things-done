package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.shared.libraries.validation.api.web.rest.ValidationErrorsDto;
import com.smalaca.gtd.usermanagement.domain.user.UserAlreadyExistsException;
import com.smalaca.gtd.usermanagement.domain.user.UserAlreadyExistsExceptionTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static com.smalaca.gtd.shared.libraries.validation.api.web.rest.ValidationFieldErrorDtoAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

class UserControllerAdviceTest {
    private final UserControllerAdvice advice = new UserControllerAdvice();

    @Test
    void shouldHandleException() {
        UserAlreadyExistsException exception = givenExceptionFor("1R0N M4N");

        ResponseEntity<ValidationErrorsDto> actual = advice.handle(exception);

        assertThat(actual.getStatusCode()).isEqualTo(OK);
        assertThat(actual.getBody().getErrors())
                .hasSize(1)
                .allSatisfy(error -> assertThat(error)
                        .forField("userName")
                        .hasMessage("User \"1R0N M4N\" already exists."));
    }

    private UserAlreadyExistsException givenExceptionFor(String userName) {
        return new UserAlreadyExistsExceptionTestFactory().create(userName);
    }
}