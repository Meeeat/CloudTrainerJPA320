package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.TweetDto;
import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetService {

    private final TweetRepository repository;
    private final TweetDto mapper;

    public TweetService(TweetRepository repository, TweetDto mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<TweetResponseTo> getAll() {
        return repository.findAll().stream()
                .map(mapper::out)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<TweetResponseTo> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public Page<TweetResponseTo> getAll(Specification<Tweet> spec, Pageable pageable) {
        return repository.findAll(spec, pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public TweetResponseTo get(Long id) {
        return repository.findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new NoSuchElementException("Tweet not found with id: " + id));
    }

    @Transactional
    public TweetResponseTo create(TweetRequestTo inputDto) {
        Tweet entity = mapper.in(inputDto);
        entity.setId(null);
        entity.setCreated(LocalDateTime.now());
        Tweet savedEntity = repository.save(entity);
        return mapper.out(savedEntity);
    }

    @Transactional
    public TweetResponseTo update(TweetRequestTo inputDto) {
        if (inputDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        if (!repository.existsById(inputDto.getId())) {
            throw new NoSuchElementException("Tweet not found with id: " + inputDto.getId());
        }

        Tweet entity = mapper.in(inputDto);
        entity.setModified(LocalDateTime.now());
        Tweet updatedEntity = repository.save(entity);
        return mapper.out(updatedEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<TweetResponseTo> getByCreatorId(Long creatorId) {
        return repository.findByCreatorId(creatorId).stream()
                .map(mapper::out)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<TweetResponseTo> getByCreatorId(Long creatorId, Pageable pageable) {
        return repository.findByCreatorId(creatorId, pageable)
                .map(mapper::out);
    }
}