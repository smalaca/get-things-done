package com.smalaca.gtd.usermanagement.persistence.user;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.smalaca.gtd.usermanagement.persistence.user.Role.USER;

public class UserAssertion {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final User actual;

    private UserAssertion(User actual) {
        this.actual = actual;
    }

    public static UserAssertion assertThat(User actual) {
        return new UserAssertion(actual);
    }

    public UserAssertion hasUserName(String expected) {
        Assertions.assertThat(actual).extracting("userName").isEqualTo(expected);
        return this;
    }

    public UserAssertion hasEncodedPassword(String expected) {
        password().isInstanceOfSatisfying(String.class, encoded -> {
            boolean actual = PASSWORD_ENCODER.matches(expected, encoded);
            Assertions.assertThat(actual).isTrue();
        });
        return this;
    }

    public UserAssertion hasPassword(String expected) {
        password().isEqualTo(expected);
        return this;
    }

    private AbstractObjectAssert<?, ?> password() {
        return Assertions.assertThat(actual).extracting("password");
    }

    public UserAssertion isActive() {
        Assertions.assertThat(actual).extracting("active").isEqualTo(true);
        return this;
    }

    public UserAssertion hasUserRole() {
        Assertions.assertThat(actual).extracting("role").isEqualTo(USER);
        return this;
    }
}
