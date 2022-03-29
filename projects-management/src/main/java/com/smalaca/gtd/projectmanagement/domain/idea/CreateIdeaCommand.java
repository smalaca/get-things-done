package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor(access = PRIVATE)
@Getter(PACKAGE)
public class CreateIdeaCommand {
    private final AuthorId authorId;
    private final String title;
    private final String description;

    public static CreateIdeaCommand create(UUID authorId, String title, String description) {
        return new CreateIdeaCommand(AuthorId.from(authorId), title, description);
    }

    boolean hasNoTitle() {
        return isBlank(title);
    }

    boolean hasNoDescription() {
        return isBlank(description);
    }
}
