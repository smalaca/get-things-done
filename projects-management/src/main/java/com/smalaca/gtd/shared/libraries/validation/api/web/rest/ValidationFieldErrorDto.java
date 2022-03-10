package com.smalaca.gtd.shared.libraries.validation.api.web.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

@Getter
@RequiredArgsConstructor(access = PACKAGE)
public final class ValidationFieldErrorDto {
    private final List<String> fields;
    private final String message;
}
