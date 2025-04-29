package com.cloudtrainerjpa320.repository.impl;

import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class TweetRepoImpl implements Repo<Tweet> {
    Map<Long, Tweet> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Tweet> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Tweet> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Tweet> create(Tweet input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Tweet> update(Tweet input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
