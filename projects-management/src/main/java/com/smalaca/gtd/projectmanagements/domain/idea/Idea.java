package com.smalaca.gtd.projectmanagements.domain.idea;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressWarnings("PMD.UnusedPrivateField")
@SuppressFBWarnings("URF_UNREAD_FIELD")
public class Idea {
    private String title;
    private String description;

    Idea(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
