package com.cloudtrainerjpa320.repository.impl;


import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class CreatorRepoImpl implements Repo<Creator> {
    Map<Long, Creator> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Creator> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Creator> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Creator> create(Creator input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Creator> update(Creator input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
