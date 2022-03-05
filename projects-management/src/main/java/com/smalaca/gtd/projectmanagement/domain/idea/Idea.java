package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.owner.OwnerId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
    @Column(name = "idea_id")
    private UUID id;

    private String title;
    @Lob
    private String description;

    @Embedded
    private OwnerId ownerId;

    private Idea() {}

    Idea(OwnerId ownerId, String title, String description) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
    }

    public IdeaId id() {
        return IdeaId.from(id);
    }
}
