package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.owner.OwnerId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor(access = PRIVATE)
@Getter(PACKAGE)
public class CreateIdeaCommand {
    private final OwnerId ownerId;
    private final String title;
    private final String description;

    public static CreateIdeaCommand create(UUID ownerId, String title, String description) {
        return new CreateIdeaCommand(OwnerId.from(ownerId), title, description);
    }

    boolean hasNoTitle() {
        return isBlank(title);
    }

    boolean hasNoDescription() {
        return isBlank(description);
    }
}
