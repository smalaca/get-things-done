package com.smalaca.gtd.projectmanagement.query.idea;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IdeaQueryService {
    private final IdeaQueryRepository repository;

    IdeaQueryService(IdeaQueryRepository repository) {
        this.repository = repository;
    }

    public List<IdeaReadModel> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    public Optional<IdeaReadModel> findById(UUID id) {
        return repository.findById(id);
    }
}
