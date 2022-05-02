package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorDoesNotExistException;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaDoesNotExistException;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("idea")
public class IdeaRestController {
    private final IdeaFacade ideaFacade;

    IdeaRestController(IdeaFacade ideaFacade) {
        this.ideaFacade = ideaFacade;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String create(@Valid @RequestBody IdeaCreateDto dto, Authentication authentication) {
        return ideaFacade.create(dto, authentication);
    }

    @GetMapping
    public List<IdeaDto> findAll() {
        return ideaFacade.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaDto> findBy(@PathVariable UUID id) {
        Optional<IdeaDto> found = ideaFacade.findBy(id);

        if (found.isPresent()) {
            return ok(found.get());
        } else {
            return notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> share(@PathVariable UUID id, @RequestBody IdeaShareDto dto, Authentication authentication) {
        try {
            ideaFacade.share(id, dto, authentication);
            return ok().build();
        } catch (CollaboratorDoesNotExistException | IdeaDoesNotExistException exception) {
            return notFound().build();
        }
    }
}
