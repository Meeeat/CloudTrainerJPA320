package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.service.TweetService;
import jakarta.validation.Valid;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseTo create(@RequestBody @Valid TweetRequestTo inputDto) {
        return tweetService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TweetResponseTo update(@RequestBody @Valid TweetRequestTo inputDto) {
        try {
            return tweetService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public TweetResponseTo read(@PathVariable long id) {
        return tweetService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = tweetService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
