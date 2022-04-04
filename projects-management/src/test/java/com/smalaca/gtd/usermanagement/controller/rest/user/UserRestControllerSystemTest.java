package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.client.rest.ProjectsManagementClient;
import com.smalaca.gtd.client.rest.user.UserTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDto;
import com.smalaca.gtd.tests.annotation.RestControllerTest;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUserManagementTestConfiguration;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static com.smalaca.gtd.client.rest.RestClientResponseAssertions.assertThat;
import static com.smalaca.gtd.usermanagement.persistence.user.UserAssertion.assertThat;
import static org.springframework.http.HttpStatus.OK;

@RestControllerTest
@Import(GivenUserManagementTestConfiguration.class)
class UserRestControllerSystemTest {
    private static final String USER_NAME = "peterparker";
    private static final String PASSWORD = "1H4teSpiders!";

    @Autowired private ProjectsManagementClient client;
    @Autowired private UserTestRepository repository;
    @Autowired private GivenUsers givenUsers;

    private UUID id;

    @AfterEach
    void tearDown() {
        givenUsers.deleteAll();
    }

    @Test
    void shouldRegisterNewUser() {
        id = client.user().create(userDto()).asUuid();

        givenUsers.existing(id);
        assertThat(repository.findBy(id))
                .hasUserName(USER_NAME)
                .hasEncodedPassword(PASSWORD)
                .isActive()
                .hasUserRole();
    }

    @Test
    void shouldNotAllowToRegisterNewUser() {
        UserTestDto.UserTestDtoBuilder user = UserTestDto.builder().password("invalid");

        ValidationErrorsTestDto actual = client.user(OK).create(user).asValidationErrors();

        assertThat(actual)
                .hasErrors(3)
                .hasErrorThat(error -> assertThat(error)
                        .hasField("userName")
                        .hasMessage("User Name needs to have at least 8 characters."))
                .hasErrorThat(error -> assertThat(error)
                        .hasField("password")
                        .hasMessage(
                                "Password needs to have at least 8 characters including: " +
                                "one capital letter, one small letter, one number, one special character."))
                .hasErrorThat(error -> assertThat(error)
                        .hasFields("password", "repeatedPassword")
                        .hasMessage("Password and repeated password have to be the same"));
    }


    @Test
    void shouldNotAllowRegisterSameUserTwice() {
        givenUsers.existing(USER_NAME, PASSWORD);

        ValidationErrorsTestDto actual = client.user(OK).create(userDto()).asValidationErrors();

        assertThat(actual)
                .hasErrors(1)
                .hasErrorThat(error -> assertThat(error)
                        .hasField("userName")
                        .hasMessage("User \"peterparker\" already exists."));
    }

    private UserTestDto.UserTestDtoBuilder userDto() {
        return UserTestDto.builder()
                .userName(USER_NAME)
                .password(PASSWORD)
                .repeatedPassword(PASSWORD);
    }
}