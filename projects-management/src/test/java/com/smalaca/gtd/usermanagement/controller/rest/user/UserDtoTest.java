package com.smalaca.gtd.usermanagement.controller.rest.user;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest {
    private static final String VALID_USERNAME = "W4ndaM4ximoff";
    private static final String VALID_PASSWORD = "P455word@#$";

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("invalidUserDto")
    void shouldRecognizeInvalidUserDtoWhenNoDataGiven(String userName, String password) {
        UserDto dto = new UserDto(userName, password, password);

        Set<ConstraintViolation<UserDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(2)
                .anySatisfy(this::hasInvalidUserName)
                .anySatisfy(this::hasInvalidPassword);
    }

    public static Stream<Arguments> invalidUserDto() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""),
                Arguments.of(null, "  "),
                Arguments.of("", null),
                Arguments.of("", ""),
                Arguments.of("", "  "),
                Arguments.of("  ", null),
                Arguments.of("  ", ""),
                Arguments.of("  ", "  ")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"short", "shorter"})
    void shouldRecognizeInvalidUserDtoWhenInvalidUserNameGiven(String userName) {
        UserDto dto = new UserDto(userName, VALID_PASSWORD, VALID_PASSWORD);

        Set<ConstraintViolation<UserDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(1)
                .anySatisfy(this::hasInvalidUserName);
    }

    private void hasInvalidUserName(ConstraintViolation<UserDto> violation) {
        hasInvalidValueFor(violation, "userName", "User Name needs to have at least 8 characters.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"shorttt", "no_cap1t4l!", "NO_SM4LL!", "NO_Number!", "N0sp3cialCharacter"})
    void shouldRecognizeInvalidUserDtoWhenInvalidPasswordGiven(String password) {
        UserDto dto = new UserDto(VALID_USERNAME, password, password);

        Set<ConstraintViolation<UserDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(1)
                .anySatisfy(this::hasInvalidPassword);
    }

    private void hasInvalidPassword(ConstraintViolation<UserDto> violation) {
        hasInvalidValueFor(violation, "password",
                "Password needs to have at least 8 characters including: " +
                "one capital letter, one small letter, one number, one special character.");
    }

    private void hasInvalidValueFor(ConstraintViolation<UserDto> violation, String fieldName, String message) {
        assertThat(violation.getPropertyPath().toString()).isEqualTo(fieldName);
        assertThat(violation.getMessage()).isEqualTo(message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"P4ssword!", "AAAbbbccc@123", "Hello world$123", "A[{}]:;',?/*a1", "0123456789$abcdefgAB"})
    void shouldRecognizeInvalidUserDtoWhenInvalidRepeatedPasswordGiven(String repeatedPassword) {
        UserDto dto = new UserDto(VALID_USERNAME, VALID_PASSWORD, repeatedPassword);

        Set<ConstraintViolation<UserDto>> actual = validator.validate(dto);

        assertThat(actual)
                .hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getMessage()).isEqualTo("Password and repeated password have to be the same");
                });
    }

    @ParameterizedTest
    @MethodSource("validUserDto")
    void shouldRecognizeValidUserDto(String userName, String password) {
        UserDto dto = new UserDto(userName, password, password);

        Set<ConstraintViolation<UserDto>> actual = validator.validate(dto);

        assertThat(actual).isEmpty();
    }

    public static Stream<Arguments> validUserDto() {
        return Stream.of(
                Arguments.of(VALID_USERNAME, VALID_PASSWORD),
                Arguments.of("wandamaximoff", VALID_PASSWORD),
                Arguments.of("12345678", VALID_PASSWORD),
                Arguments.of("WANDAMAXIMOFF", VALID_PASSWORD),

                Arguments.of(VALID_USERNAME, "P4ssword!"),
                Arguments.of(VALID_USERNAME, "AAAbbbccc@123"),
                Arguments.of(VALID_USERNAME, "Hello world$123"),
                Arguments.of(VALID_USERNAME, "A!@#&()–a1"),
                Arguments.of(VALID_USERNAME, "A[{}]:;',?/*a1"),
                Arguments.of(VALID_USERNAME, "A~$^+=<>a1"),
                Arguments.of(VALID_USERNAME, "0123456789$abcdefgAB"),
                Arguments.of(VALID_USERNAME, "123Aa$Aa")
        );
    }
}