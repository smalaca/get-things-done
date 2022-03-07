package com.smalaca.gtd.shared.configuration.api.web.rest.validationerror;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class ValidationFieldErrorDto {
    private final List<String> fields;
    private final String message;
}
