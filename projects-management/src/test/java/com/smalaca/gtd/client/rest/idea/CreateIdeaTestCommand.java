package com.smalaca.gtd.client.rest.idea;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class CreateIdeaTestCommand {
    private String title;
    private String description;
}
