package com.smalaca.gtd.client.rest.idea;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PACKAGE)
public class IdeaShareTestDto {
    private UUID collaboratorId;
}
