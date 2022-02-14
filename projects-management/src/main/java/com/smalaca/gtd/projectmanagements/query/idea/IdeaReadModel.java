package com.smalaca.gtd.projectmanagements.query.idea;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@NoArgsConstructor(access = PRIVATE)
public class IdeaReadModel {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    @Lob
    private String description;

    IdeaReadModel(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
