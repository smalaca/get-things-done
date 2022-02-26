package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.usermanagement.domain.user.User;
import com.smalaca.gtd.usermanagement.domain.user.UserFactory;
import com.smalaca.gtd.usermanagement.domain.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserFactory factory;
    private final UserRepository repository;

    UserRestController(UserRepository repository, UserFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody UserDto dto) {
        User user = factory.create(dto.getUserName(), dto.getPassword());
        return repository.save(user);
    }
}
