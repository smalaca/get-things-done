package com.smalaca.gtd.projectmanagements.domain.idea;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@SuppressWarnings("PMD.UnusedPrivateField")
@SuppressFBWarnings("URF_UNREAD_FIELD")
@Entity
@Table(name = "IDEAS")
public class Idea {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    @Lob
    private String description;

    private Idea() {}

    Idea(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public UUID id() {
        return id;
    }
}
