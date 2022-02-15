package com.smalaca.gtd.projectmanagements.infrastructure.api.web.rest.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class IdeaTestDto {
    private UUID id;
    private String title;
    private String description;
}
