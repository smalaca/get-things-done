package com.smalaca.gtd.projectmanagements.domain.idea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PACKAGE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor
@Getter(PACKAGE)
public class CreateIdeaCommand {
    private final String title;
    private final String description;

    boolean hasTitle() {
        return isBlank(title);
    }

    boolean hasDescription() {
        return isBlank(description);
    }
}
