package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.application.idea.IdeaApplicationService;
import com.smalaca.gtd.projectmanagement.application.idea.ShareIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaQueryService;
import com.smalaca.gtd.projectmanagement.query.user.UserQueryService;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

class IdeaFacade {
    private final IdeaApplicationService ideaApplicationService;
    private final IdeaQueryService ideaQueryService;
    private final UserQueryService userQueryService;
    private final IdeaDtoFactory ideaDtoFactory;

    IdeaFacade(
            IdeaApplicationService ideaApplicationService, IdeaQueryService ideaQueryService,
            UserQueryService userQueryService, IdeaDtoFactory ideaDtoFactory) {
        this.ideaApplicationService = ideaApplicationService;
        this.ideaQueryService = ideaQueryService;
        this.userQueryService = userQueryService;
        this.ideaDtoFactory = ideaDtoFactory;
    }

    String create(IdeaDto dto, Authentication authentication) {
        CreateIdeaCommand command = dto.asCreateIdeaCommand(authorId(authentication));
        return ideaApplicationService.create(command).toString();
    }

    void share(UUID ideaId, IdeaShareDto dto, Authentication authentication) {
        ShareIdeaCommand command = dto.asShareIdeaCommand(authorId(authentication), ideaId);
        ideaApplicationService.share(command);
    }

    private UUID authorId(Authentication authentication) {
        return userQueryService.findByUserName(authentication.getName()).getId();
    }

    List<IdeaDto> findAll() {
        return ideaQueryService.findAll().stream()
                .map(ideaDtoFactory::create)
                .collect(toList());
    }

    Optional<IdeaDto> findBy(UUID id) {
        return ideaQueryService.findById(id)
                .map(ideaDtoFactory::create);
    }
}
