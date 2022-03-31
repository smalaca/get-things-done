package com.smalaca.gtd.projectmanagement.domain.idea;

import com.smalaca.gtd.projectmanagement.domain.author.AuthorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorException;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

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
    private AuthorId authorId;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "idea_id"))
    private Set<CollaboratorId> collaborators = new HashSet<>();

    private Idea() {}

    Idea(AuthorId authorId, String title, String description) {
        this.authorId = authorId;
        this.title = title;
        this.description = description;
    }

    public IdeaId id() {
        return IdeaId.from(id);
    }

    public void share(CollaboratorRepository collaboratorRepository, CollaboratorId collaboratorId) {
        if (collaboratorRepository.existsBy(collaboratorId)) {
            collaborators.add(collaboratorId);
        } else {
            throw new CollaboratorException(collaboratorId);
        }
    }
}
