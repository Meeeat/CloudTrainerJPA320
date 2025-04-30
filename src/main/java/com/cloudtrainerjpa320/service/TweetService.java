package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.TweetDto;
import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository repository;
    private final CreatorRepository creatorRepository;
    private final TweetDto mapper;

    @Transactional(readOnly = true)
    public List<TweetResponseTo> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<TweetResponseTo> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public TweetResponseTo get(Long id) {
        return repository
                .findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new EntityNotFoundException("Tweet not found with id: " + id));
    }

    @Transactional
    public TweetResponseTo create(TweetRequestTo input) {
        Tweet entity = mapper.in(input);
        entity.setId(null);

        entity.setCreator(creatorRepository.findById(input.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("create Creator in Tweet not found with id: " + input.getCreatorId())));

        if (entity.getCreated() == null) {
            entity.setCreated(LocalDateTime.now());
        }

        return mapper.out(repository.save(entity));
    }

    @Transactional
    public TweetResponseTo update(TweetRequestTo input) {
        if (input.getId() == null || !repository.existsById(input.getId())) {
            throw new EntityNotFoundException("Tweet not found with id: " + input.getId());
        }

        Tweet entity = mapper.in(input);

        entity.setCreator(creatorRepository.findById(input.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("update Creator in Tweet not found with id: " + input.getCreatorId())));

        entity.setModified(LocalDateTime.now());

        return mapper.out(repository.save(entity));
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}