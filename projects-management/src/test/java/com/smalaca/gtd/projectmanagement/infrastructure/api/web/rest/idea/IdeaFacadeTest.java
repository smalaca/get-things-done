package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.smalaca.gtd.projectmanagement.application.idea.IdeaApplicationService;
import com.smalaca.gtd.projectmanagement.application.idea.ShareIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.query.GivenReadModel;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaQueryService;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModel;
import com.smalaca.gtd.projectmanagement.query.user.UserQueryService;
import com.smalaca.gtd.projectmanagement.query.user.UserReadModel;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class IdeaFacadeTest {
    private static final UUID IDEA_ID = id();
    private static final UUID AUTHOR_ID = id();
    private static final String TITLE = "The Greatest Show";
    private static final String DESCRIPTION = "with the greatest people";
    private static final String USER_NAME = "Steve Rogers";
    private static final UUID COLLABORATOR_ID = id();

    private final GivenReadModel given = new GivenReadModel();

    private final IdeaApplicationService ideaApplicationService = mock(IdeaApplicationService.class);
    private final IdeaQueryService ideaQueryService = mock(IdeaQueryService.class);
    private final UserQueryService userQueryService = mock(UserQueryService.class);
    private final IdeaFacade facade = new IdeaFacadeFactory().create(ideaApplicationService, ideaQueryService, userQueryService);

    private static UUID id() {
        return UUID.randomUUID();
    }

    @Test
    void shouldCreateIdea() {
        CreateIdeaCommand command = CreateIdeaCommand.create(AUTHOR_ID, TITLE, DESCRIPTION);
        given(ideaApplicationService.create(command)).willReturn(IDEA_ID);
        IdeaDto dto = IdeaDto.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        String actual = facade.create(dto, givenExistingUser());

        assertThat(actual).isEqualTo(IDEA_ID.toString());
    }

    @Test
    void shouldShareIdea() {
        ShareIdeaCommand expected = new ShareIdeaCommand(AUTHOR_ID, IDEA_ID, COLLABORATOR_ID);

        facade.share(IDEA_ID, new IdeaShareDto(COLLABORATOR_ID), givenExistingUser());

        then(ideaApplicationService).should().share(expected);
    }

    private Authentication givenExistingUser() {
        UserReadModel user = given.user().id(AUTHOR_ID).build();
        given(userQueryService.findByUserName(USER_NAME)).willReturn(user);

        return authentication();
    }

    private Authentication authentication() {
        Authentication authenticate = mock(Authentication.class);
        given(authenticate.getName()).willReturn(USER_NAME);
        return authenticate;
    }

    @Test
    void shouldFindNoIdeasWhenNothingFound() {
        given(ideaQueryService.findAll()).willReturn(emptyList());

        List<IdeaDto> actual = facade.findAll();

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFindAllIdeas() {
        UUID id1 = id();
        UUID id2 = id();
        UUID id3 = id();
        List<IdeaReadModel> ideas = ImmutableList.of(
                given.idea().id(id1).title("IdeaOne").description("Nothing to talk about").build(),
                given.idea().id(id2).title("IdeaTwo").build(),
                given.idea().id(id3).description("There's a lot to talk about").build()
        );
        given(ideaQueryService.findAll()).willReturn(ideas);

        List<IdeaDto> actual = facade.findAll();

        assertThat(actual).hasSize(3)
                .anySatisfy(idea -> {
                    assertThat(idea).isEqualTo(expectedDto(id1, "IdeaOne", "Nothing to talk about"));
                }).anySatisfy(idea -> {
                    assertThat(idea).isEqualTo(expectedDto(id2, "IdeaTwo", null));
                }).anySatisfy(idea -> {
                    assertThat(idea).isEqualTo(expectedDto(id3, null, "There's a lot to talk about"));
                });
    }

    private IdeaDto expectedDto(UUID id, String title, String description) {
        return IdeaDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .collaborators(emptySet())
                .build();
    }

    @Test
    void shouldFindNoIdeaWhenNothingFound() {
        given(ideaQueryService.findById(IDEA_ID)).willReturn(empty());

        Optional<IdeaDto> actual = facade.findBy(IDEA_ID);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFindOneIdea() {
        UUID id1 = id();
        UUID id2 = id();
        UUID id3 = id();
        IdeaDto expected = IdeaDto.builder()
                .id(IDEA_ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .collaborators(ImmutableSet.of(
                        expectedCollaborator(id1, "tony stark"),
                        expectedCollaborator(id2, "steve rogers"),
                        expectedCollaborator(id3, "peter parker")
                ))
                .build();
        IdeaReadModel idea = given.idea()
                .id(IDEA_ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .collaborator(id1, "tony stark")
                .collaborator(id2, "steve rogers")
                .collaborator(id3, "peter parker")
                .build();
        given(ideaQueryService.findById(IDEA_ID)).willReturn(Optional.of(idea));

        Optional<IdeaDto> actual = facade.findBy(IDEA_ID);

        assertThat(actual.get()).isEqualTo(expected);
    }

    private CollaboratorDto expectedCollaborator(UUID id1, String userName) {
        return CollaboratorDto.asDto(given.user().id(id1).userName(userName).build());
    }
}