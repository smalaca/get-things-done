package com.smalaca.gtd.client.rest.validation;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationFieldErrorTestDto {
    private List<String> fields;
    private String message;
}
