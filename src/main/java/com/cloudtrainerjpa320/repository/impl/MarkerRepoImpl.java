package com.cloudtrainerjpa320.repository.impl;

import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class MarkerRepoImpl implements Repo<Marker> {
    Map<Long, Marker> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Marker> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Marker> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Marker> create(Marker input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Marker> update(Marker input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
