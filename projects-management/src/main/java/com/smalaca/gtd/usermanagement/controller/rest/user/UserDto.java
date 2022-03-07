package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.shared.libraries.validation.constrains.SameValues;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
@SameValues(
        fields = {"password", "repeatedPassword"},
        message = "Password and repeated password have to be the same"
)
public class UserDto {
    private static final String INVALID_PASSWORD_MESSAGE =
            "Password needs to have at least 8 characters including: "
            + "one capital letter, one small letter, one number, one special character.";
    private static final String INVALID_USER_NAME_MESSAGE = "User Name needs to have at least 8 characters.";
    private static final int MAX_USER_NAME_LENGTH = 8;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    @NotNull(message = INVALID_USER_NAME_MESSAGE)
    @Length(min = MAX_USER_NAME_LENGTH, message = INVALID_USER_NAME_MESSAGE)
    private final String userName;

    @NotNull(message = INVALID_PASSWORD_MESSAGE)
    @Pattern(regexp = PASSWORD_PATTERN, message = INVALID_PASSWORD_MESSAGE)
    private final String password;

    private final String repeatedPassword;
}
