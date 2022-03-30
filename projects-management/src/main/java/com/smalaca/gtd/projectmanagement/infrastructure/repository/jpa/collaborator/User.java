package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.collaborator;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity(name = "ProjectManagementUser")
@Table(name = "USERS")
@NoArgsConstructor(access = PRIVATE)
class User {
    @Id
    @GeneratedValue
    private UUID id;
}
