package com.cloudtrainerjpa320.service.impl;

import com.cloudtrainerjpa320.exception.DuplicateTitleException;
import com.cloudtrainerjpa320.exception.ResourceNotFoundException;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import com.cloudtrainerjpa320.repository.MarkerRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import com.cloudtrainerjpa320.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TweetServiceImpl implements BaseService<Tweet> {

    private final TweetRepository tweetRepository;
    private final CreatorRepository creatorRepository;
    private final MarkerRepository markerRepository;
    public TweetServiceImpl(TweetRepository tweetRepository, CreatorRepository creatorRepository, MarkerRepository markerRepository) {
        this.tweetRepository = tweetRepository;
        this.creatorRepository = creatorRepository;
        this.markerRepository = markerRepository;
    }

    @Override
    public Tweet create(Tweet tweet) {
        if (tweet.getCreator() != null && tweet.getCreator().getId() != null) {
            creatorRepository.findById(tweet.getCreator().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Creator with ID " + tweet.getCreator().getId() + " not found"));
        }

        if (tweetRepository.existsByTitle(tweet.getTitle())) {
            throw new DuplicateTitleException(tweet.getTitle());
        }

        LocalDateTime now = LocalDateTime.now();
        tweet.setCreated(now);
        tweet.setModified(now);

        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional(readOnly = true)
    public Tweet getById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tweet> getAll() {
        return tweetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tweet> getAll(Pageable pageable) {
        return tweetRepository.findAll(pageable);
    }

    @Override
    public Tweet update(Long id, Tweet tweet) {
        Tweet existingTweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with ID " + id + " not found"));

        if (tweet.getCreator() != null && tweet.getCreator().getId() != null) {
            creatorRepository.findById(tweet.getCreator().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Creator with ID " + tweet.getCreator().getId() + " not found"));
        }

        tweet.setId(id);
        tweet.setCreated(existingTweet.getCreated());
        tweet.setModified(LocalDateTime.now());

        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteById(Long id) {
        if (!tweetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tweet with ID " + id + " not found");
        }
        tweetRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Tweet> findByCreatorId(Long creatorId, Pageable pageable) {
        return tweetRepository.findByCreatorId(creatorId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Tweet> findByTitleContaining(String title, Pageable pageable) {
        return tweetRepository.findByTitleContaining(title, pageable);
    }
}