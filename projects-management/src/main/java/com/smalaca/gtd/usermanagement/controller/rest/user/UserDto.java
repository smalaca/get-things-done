package com.smalaca.gtd.usermanagement.controller.rest.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {
    private final String userName;
    private final String password;
}
