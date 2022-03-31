package com.smalaca.gtd.projectmanagement.query.user;

import com.smalaca.gtd.tests.annotation.RepositoryTest;
import com.smalaca.gtd.usermanagement.persistence.user.UserTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.smalaca.gtd.usermanagement.persistence.user.UserTestFactory.user;
import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@Import(UserTestRepository.class)
class UserQueryServiceIntegrationTest {
    @Autowired private UserTestRepository userTestRepository;
    @Autowired private UserQueryRepository userQueryRepository;
    private final List<UUID> ids = new ArrayList<>();

    private UserQueryService userQueryService;

    @BeforeEach
    void initUserQueryService() {
        userQueryService = new UserQueryService(userQueryRepository);
    }

    @AfterEach
    void deleteUsers() {
        ids.forEach(userTestRepository::deleteBy);
    }

    @Test
    void shouldFindSpecificUser() {
        givenUsers();
        UUID id = givenUser("mary jane watson", "M4RRy");

        UserReadModel actual = userQueryService.findByUserName("mary jane watson");

        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getUserName()).isEqualTo("mary jane watson");
    }

    private void givenUsers() {
        givenUser("captain-america", "5H13LD");
        givenUser("peter parker", "web");
        givenUser("gwen stacy", "ghost");
    }

    private UUID givenUser(String userName, String password) {
        UUID id = userTestRepository.save(user(userName, password));
        ids.add(id);
        return id;
    }
}