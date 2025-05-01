package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.service.TweetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public Collection<TweetResponseTo> getAll() {
        return tweetService.getAll();
    }

    @GetMapping("/paged")
    public Page<TweetResponseTo> getAllPaged(@PageableDefault(size = 20) Pageable pageable) {
        return tweetService.getAll(pageable);
    }

    @GetMapping("/creator/{creatorId}")
    public Collection<TweetResponseTo> getByCreatorId(@PathVariable Long creatorId) {
        return tweetService.getByCreatorId(creatorId);
    }

    @GetMapping("/creator/{creatorId}/paged")
    public Page<TweetResponseTo> getByCreatorIdPaged(
            @PathVariable Long creatorId,
            @PageableDefault(size = 20) Pageable pageable) {
        return tweetService.getByCreatorId(creatorId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseTo create(@RequestBody @Valid TweetRequestTo inputDto) {
        try {
            return tweetService.create(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TweetResponseTo update(@RequestBody @Valid TweetRequestTo inputDto) {
        try {
            return tweetService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public TweetResponseTo read(@PathVariable long id) {
        try {
            return tweetService.get(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean deleted = tweetService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found with id: " + id);
        }
    }
}