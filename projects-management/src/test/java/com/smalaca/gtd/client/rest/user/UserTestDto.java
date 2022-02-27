package com.smalaca.gtd.client.rest.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserTestDto {
    private String userName;
    private String password;
    private String repeatedPassword;
}
