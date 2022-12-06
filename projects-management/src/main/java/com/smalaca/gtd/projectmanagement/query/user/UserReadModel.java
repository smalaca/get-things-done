package com.smalaca.gtd.projectmanagement.query.user;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = PRIVATE)
@Getter
public class UserReadModel {
    @Id
    @GeneratedValue
    private UUID id;
    private String userName;
}
