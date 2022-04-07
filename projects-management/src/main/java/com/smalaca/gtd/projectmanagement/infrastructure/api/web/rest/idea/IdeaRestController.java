package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.application.idea.IdeaApplicationService;
import com.smalaca.gtd.projectmanagement.application.idea.ShareIdeaCommand;
import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaQueryService;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModel;
import com.smalaca.gtd.projectmanagement.query.user.UserQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("idea")
public class IdeaRestController {
    private final IdeaApplicationService ideaApplicationService;
    private final IdeaQueryService ideaQueryService;
    private final UserQueryService userQueryService;
    private final IdeaDtoFactory ideaDtoFactory;

    IdeaRestController(
            IdeaApplicationService ideaApplicationService, IdeaQueryService ideaQueryService, UserQueryService userQueryService,
            IdeaDtoFactory ideaDtoFactory) {
        this.ideaApplicationService = ideaApplicationService;
        this.ideaQueryService = ideaQueryService;
        this.userQueryService = userQueryService;
        this.ideaDtoFactory = ideaDtoFactory;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String create(@Valid @RequestBody IdeaDto dto, Authentication authentication) {
        CreateIdeaCommand command = dto.asCreateIdeaCommand(authorIdFrom(authentication));

        return ideaApplicationService.create(command).toString();
    }

    @GetMapping
    public List<IdeaDto> findAll() {
        return ideaQueryService.findAll().stream()
                .map(ideaDtoFactory::create)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaDto> findBy(@PathVariable UUID id) {
        Optional<IdeaReadModel> found = ideaQueryService.findById(id);

        if (found.isPresent()) {
            return ResponseEntity.ok(ideaDtoFactory.create(found.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public void share(@PathVariable UUID id, @RequestBody IdeaShareDto dto, Authentication authentication) {
        ShareIdeaCommand command = dto.asShareIdeaCommand(authorIdFrom(authentication), id);
        ideaApplicationService.share(command);
    }

    private UUID authorIdFrom(Authentication authentication) {
        return userQueryService.findByUserName(authentication.getName()).getId();
    }
}
