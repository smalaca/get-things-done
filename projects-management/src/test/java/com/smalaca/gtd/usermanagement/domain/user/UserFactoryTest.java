package com.smalaca.gtd.usermanagement.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserFactoryTest {
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserRepository repository = mock(UserRepository.class);
    private final UserFactory factory = new UserFactory(repository, passwordEncoder);

    @Test
    void shouldCreateUser() {
        given(repository.exists("peterparker")).willReturn(false);
        given(passwordEncoder.encode("password")).willReturn("encoded-password");

        User actual = factory.create("peterparker", "password");

        UserAssertion.assertThat(actual)
                .hasUserName("peterparker")
                .hasPassword("encoded-password")
                .isActive()
                .hasUserRole();
    }

    @Test
    void shouldRecognizeUserWithGivenUserNameAlreadyExists() {
        given(repository.exists("peterparker")).willReturn(true);

        UserAlreadyExistsException actual = assertThrows(
                UserAlreadyExistsException.class, () -> factory.create("peterparker", "password"));

        assertThat(actual).hasMessage("User \"peterparker\" already exists.");
    }
}