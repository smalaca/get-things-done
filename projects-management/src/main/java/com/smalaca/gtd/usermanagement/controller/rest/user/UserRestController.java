package com.smalaca.gtd.usermanagement.controller.rest.user;

import com.smalaca.gtd.usermanagement.domain.user.UserService;
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
    private final UserService userService;

    UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody UserDto dto) {
        return userService.register(dto.getUserName(), dto.getPassword());
    }
}
