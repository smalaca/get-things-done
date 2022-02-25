package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.client.rest.ProjectsManagementClient;
import com.smalaca.gtd.client.rest.user.UserTestDto;
import com.smalaca.gtd.client.rest.validation.ValidationErrorsTestDto;
import com.smalaca.gtd.tests.annotation.SystemTest;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static com.smalaca.gtd.client.rest.RestClientResponseAssertions.assertThat;
import static com.smalaca.gtd.usermanagement.persistence.user.UserAssertion.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SystemTest
@SpringBootTest
@AutoConfigureMockMvc
@Import({ProjectsManagementClient.class, UserTestRepository.class})
class UserRestControllerSystemTest {
    @Autowired private ProjectsManagementClient client;
    @Autowired private UserTestRepository repository;

    private UUID id;

    @AfterEach
    void tearDown() {
        if (id != null) {
            repository.deleteBy(id);
        }
    }

    @Test
    void shouldRegisterNewUser() {
        UserTestDto.UserTestDtoBuilder user = UserTestDto.builder()
                .userName("peterparker")
                .password("1H4teSpiders!");

        id = client.user().create(user).asUuid();

        assertThat(repository.findBy(id))
                .hasUserName("peterparker")
                .hasEncodedPassword("1H4teSpiders!")
                .isActive()
                .hasUserRole();
    }

    @Test
    void shouldNotAllowToRegisterNewUser() {
        ValidationErrorsTestDto actual = client.user(OK).create(UserTestDto.builder()).asValidationErrors();

        assertThat(actual)
                .hasErrors(2)
                .hasErrorThat(error -> assertThat(error)
                        .hasField("userName")
                        .hasMessage("User Name needs to have at least 8 characters."))
                .hasErrorThat(error -> assertThat(error)
                        .hasField("password")
                        .hasMessage(
                                "Password needs to have at least 8 characters including: " +
                                "one capital letter, one small letter, one number, one special character."));
    }
}