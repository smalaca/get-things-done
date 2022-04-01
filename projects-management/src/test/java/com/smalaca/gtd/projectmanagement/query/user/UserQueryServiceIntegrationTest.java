package com.smalaca.gtd.projectmanagement.query.user;

import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenTestConfiguration;
import com.smalaca.gtd.projectmanagement.infrastructure.given.GivenUsers;
import com.smalaca.gtd.tests.annotation.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import({UserQueryService.class, GivenTestConfiguration.class})
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
        UUID id = givenUsers.existing("mary jane watson", "M4RRy");

        UserReadModel actual = userQueryService.findByUserName("mary jane watson");

        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getUserName()).isEqualTo("mary jane watson");
    }

    private void givenUsers() {
        givenUsers.existing("captain-america", "5H13LD");
        givenUsers.existing("peter parker", "web");
        givenUsers.existing("gwen stacy", "ghost");
    }
}