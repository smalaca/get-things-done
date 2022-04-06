package com.smalaca.gtd.client.rest.idea;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class CollaboratorTestDto {
    private UUID id;
    private String userName;
}
