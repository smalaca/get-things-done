package com.smalaca.gtd.projectmanagement.query.idea;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
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
}
