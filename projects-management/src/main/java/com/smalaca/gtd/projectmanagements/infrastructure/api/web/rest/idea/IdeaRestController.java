package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagements.application.idea.IdeaApplicationService;
import com.smalaca.gtd.projectmanagements.query.idea.IdeaQueryService;
import com.smalaca.gtd.projectmanagements.query.idea.IdeaReadModel;
import org.springframework.http.ResponseEntity;
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
    private final IdeaApplicationService commandService;
    private final IdeaQueryService queryService;

    IdeaRestController(IdeaApplicationService commandService, IdeaQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String create(@Valid @RequestBody IdeaDto dto) {
        return commandService.create(dto.asCreateIdeaCommand()).toString();
    }

    @GetMapping
    public List<IdeaDto> findAll() {
        return queryService.findAll().stream()
                .map(this::asDto)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaDto> findBy(@PathVariable UUID id) {
        Optional<IdeaReadModel> found = queryService.findById(id);

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
