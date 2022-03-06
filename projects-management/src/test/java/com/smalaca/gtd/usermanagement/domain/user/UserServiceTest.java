package com.smalaca.gtd.usermanagement.domain.user;

import com.smalaca.gtd.usermanagement.persistence.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.smalaca.gtd.usermanagement.domain.user.UserAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class UserServiceTest {
    private static final String ID = UUID.randomUUID().toString();
    private static final String USER_NAME = "peterparker";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encoded-password";

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserService service = new UserServiceFactory().userService(userRepository, passwordEncoder);

    @BeforeEach
    void initEncoderAndReturnOfId() {
        given(userRepository.save(any())).willReturn(ID);
        given(passwordEncoder.encode(PASSWORD)).willReturn(ENCODED_PASSWORD);
    }

    @Test
    void shouldSaveNewlyCreatedUser() {
        givenNotExistingUserWithSameUserName();

        service.register(USER_NAME, PASSWORD);

        User actual = theUserWasSaved();
        assertThat(actual)
                .hasUserName(USER_NAME)
                .hasPassword(ENCODED_PASSWORD)
                .isActive()
                .hasUserRole();
    }

    private User theUserWasSaved() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        then(userRepository).should().save(captor.capture());
        return captor.getValue();
    }

    @Test
    void shouldReturnIdOfNewlyCreatedUser() {
        givenNotExistingUserWithSameUserName();

        String actual = service.register(USER_NAME, PASSWORD);

        assertThat(actual).isEqualTo(ID);
    }

    private void givenNotExistingUserWithSameUserName() {
        given(userRepository.exists(USER_NAME)).willReturn(false);
    }

    @Test
    void shouldNotSaveUserIfUserWithGivenUserNameAlreadyExists() {
        givenExistingUserWithSameUserName();

        UserAlreadyExistsException actual = assertThrows(
                UserAlreadyExistsException.class, () -> service.register(USER_NAME, PASSWORD));

        assertThat(actual).hasMessage("User \"peterparker\" already exists.");
        thenUserWasNotSaved();
    }

    private void thenUserWasNotSaved() {
        then(userRepository).should(never()).save(any());
    }

    private void givenExistingUserWithSameUserName() {
        given(userRepository.exists(UserServiceTest.USER_NAME)).willReturn(true);
    }
}