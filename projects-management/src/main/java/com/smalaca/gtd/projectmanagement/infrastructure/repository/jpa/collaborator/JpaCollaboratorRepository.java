package com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.collaborator;

import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorId;
import com.smalaca.gtd.projectmanagement.domain.collaborator.CollaboratorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCollaboratorRepository implements CollaboratorRepository {
    private final SpringDataJpaUserRepository repository;

    JpaCollaboratorRepository(SpringDataJpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsBy(CollaboratorId collaboratorId) {
        return repository.existsById(collaboratorId.value());
    }
}
