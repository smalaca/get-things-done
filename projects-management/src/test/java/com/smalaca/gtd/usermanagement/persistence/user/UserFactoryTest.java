package com.smalaca.gtd.usermanagement.persistence.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.smalaca.gtd.usermanagement.persistence.user.UserAssertion.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserFactoryTest {
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserFactory factory = new UserFactory(passwordEncoder);

    @Test
    void shouldCreateUser() {
        given(passwordEncoder.encode("password")).willReturn("encoded-password");

        User actual = factory.create("peterparker", "password");

        assertThat(actual)
                .hasUserName("peterparker")
                .hasPassword("encoded-password")
                .isActive()
                .hasUserRole();
    }
}