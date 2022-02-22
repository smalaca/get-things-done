package com.smalaca.gtd.usermanagement.persistence.user;

import org.assertj.core.api.Assertions;

import static com.smalaca.gtd.usermanagement.persistence.user.Role.USER;

class UserAssertion {
    private final User actual;

    private UserAssertion(User actual) {
        this.actual = actual;
    }

    static UserAssertion assertThat(User actual) {
        return new UserAssertion(actual);
    }

    UserAssertion hasUserName(String expected) {
        Assertions.assertThat(actual).extracting("userName").isEqualTo(expected);
        return this;
    }

    UserAssertion hasPassword(String expected) {
        Assertions.assertThat(actual).extracting("password").isEqualTo(expected);
        return this;
    }

    UserAssertion isActive() {
        Assertions.assertThat(actual).extracting("active").isEqualTo(true);
        return this;
    }

    UserAssertion hasUserRole() {
        Assertions.assertThat(actual).extracting("role").isEqualTo(USER);
        return this;
    }
}
