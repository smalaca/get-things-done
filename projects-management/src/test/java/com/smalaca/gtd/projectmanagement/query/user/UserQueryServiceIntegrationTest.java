package com.smalaca.gtd.projectmanagement.query.user;

import com.smalaca.gtd.tests.annotation.RepositoryTest;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUserManagementTestConfiguration;
import com.smalaca.gtd.usermanagement.persistence.given.GivenUsers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static com.smalaca.gtd.usermanagement.persistence.user.UserTestBuilder.user;
import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import({UserQueryService.class, GivenUserManagementTestConfiguration.class})
class UserQueryServiceIntegrationTest {
    @Autowired private GivenUsers givenUsers;
    @Autowired private UserQueryService userQueryService;

    @AfterEach
    void deleteUsers() {
        givenUsers.deleteAll();
    }

    @Test
    void shouldFindSpecificUser() {
        givenUsers();
        UUID id = givenUsers.existing(user("mary jane watson"));

        UserReadModel actual = userQueryService.findByUserName("mary jane watson");

        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getUserName()).isEqualTo("mary jane watson");
    }

    private void givenUsers() {
        givenUsers.existing(user("captain-america"));
        givenUsers.existing(user("peter parker"));
        givenUsers.existing(user("gwen stacy"));
    }
}