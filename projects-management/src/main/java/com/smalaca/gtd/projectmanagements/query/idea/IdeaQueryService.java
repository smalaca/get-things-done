package com.smalaca.gtd.projectmanagements.query.idea;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IdeaQueryService {
    private final IdeaQueryRepository repository;

    public IdeaQueryService(IdeaQueryRepository repository) {
        this.repository = repository;
    }

    List<IdeaReadModel> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    Optional<IdeaReadModel> findById(UUID id) {
        return repository.findById(id);
    }
}
