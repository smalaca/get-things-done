package com.smalaca.gtd.projectmanagement.query.idea;

import com.smalaca.gtd.projectmanagement.query.user.UserReadModel;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@SuppressFBWarnings("UWF_UNWRITTEN_FIELD")
@Entity
@Getter
@NoArgsConstructor(access = PRIVATE)
@Table(name = "IDEAS")
public class IdeaReadModel {
    @Id
    @GeneratedValue
    @Column(name = "idea_id")
    private UUID id;
    private String title;
    @Lob
    private String description;

    @ManyToMany
    @JoinTable(
            name = "idea_collaborators",
            joinColumns = @JoinColumn(name = "idea_id"),
            inverseJoinColumns = @JoinColumn(name = "collaborator_id"))
    private Set<UserReadModel> collaborators = new HashSet<>();
}
