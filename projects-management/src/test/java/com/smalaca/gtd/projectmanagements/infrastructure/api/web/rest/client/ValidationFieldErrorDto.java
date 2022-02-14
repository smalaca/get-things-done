package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationFieldErrorDto {
    private List<String> fields;
    private String message;
}
