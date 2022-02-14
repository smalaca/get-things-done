package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagements.application.idea.IdeaApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("idea")
public class IdeaRestController {
    private final IdeaApplicationService service;

    IdeaRestController(IdeaApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UUID create(@Valid @RequestBody IdeaDto dto) {
        return service.create(dto.asCreateIdeaCommand());
    }
}
