package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.application.idea.IdeaApplicationService;
import com.smalaca.gtd.projectmanagement.domain.idea.CreateIdeaCommand;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaQueryService;
import com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModel;
import com.smalaca.gtd.projectmanagement.query.user.UserQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

    IdeaRestController(
            IdeaApplicationService ideaApplicationService, IdeaQueryService ideaQueryService, UserQueryService userQueryService) {
        this.ideaApplicationService = ideaApplicationService;
        this.ideaQueryService = ideaQueryService;
        this.userQueryService = userQueryService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String create(@Valid @RequestBody IdeaDto dto, Authentication authentication) {
        CreateIdeaCommand command = dto.asCreateIdeaCommand(ownerIdFrom(authentication));

        return ideaApplicationService.create(command).toString();
    }

    private UUID ownerIdFrom(Authentication authentication) {
        return userQueryService.findByUserName(authentication.getName()).getId();
    }

    @GetMapping
    public List<IdeaDto> findAll() {
        return ideaQueryService.findAll().stream()
                .map(this::asDto)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaDto> findBy(@PathVariable UUID id) {
        Optional<IdeaReadModel> found = ideaQueryService.findById(id);

        if (found.isPresent()) {
            return ResponseEntity.ok(asDto(found.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private IdeaDto asDto(IdeaReadModel readModel) {
        return IdeaDto.builder()
                .id(readModel.getId())
                .title(readModel.getTitle())
                .description(readModel.getDescription())
                .build();
    }
}
