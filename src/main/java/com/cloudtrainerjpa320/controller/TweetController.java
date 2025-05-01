package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.dto.request.TweetRequestTo;
import com.cloudtrainerjpa320.dto.response.TweetResponseTo;
import com.cloudtrainerjpa320.mapper.TweetMapper;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.service.impl.TweetServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

    private final TweetServiceImpl tweetService;
    private final TweetMapper tweetMapper;

    public TweetController(TweetServiceImpl tweetService, TweetMapper tweetMapper) {
        this.tweetService = tweetService;
        this.tweetMapper = tweetMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseTo create(@Valid @RequestBody TweetRequestTo requestTo) {
        Tweet tweet = tweetMapper.toEntity(requestTo);
        return tweetMapper.toDto(tweetService.create(tweet));
    }

    @GetMapping("/{id}")
    public TweetResponseTo getById(@PathVariable Long id) {
        return tweetMapper.toDto(tweetService.getById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponseTo> getAll() {
        return tweetService.getAll().stream()
                .map(tweetMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public TweetResponseTo update(@PathVariable Long id, @Valid @RequestBody TweetRequestTo requestTo) {
        Tweet tweet = tweetMapper.toEntity(requestTo);
        return tweetMapper.toDto(tweetService.update(id, tweet));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tweetService.deleteById(id);
    }

    @GetMapping("/creator/{creatorId}")
    public Page<TweetResponseTo> getByCreatorId(@PathVariable Long creatorId, Pageable pageable) {
        return tweetService.findByCreatorId(creatorId, pageable).map(tweetMapper::toDto);
    }

    @GetMapping("/search")
    public Page<TweetResponseTo> search(@RequestParam(required = false) String title, Pageable pageable) {
        if (title != null) {
            return tweetService.findByTitleContaining(title, pageable).map(tweetMapper::toDto);
        }
        return tweetService.getAll(pageable).map(tweetMapper::toDto);
    }
}