package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.filter.NoticeFilter;
import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.service.NoticeService;
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
@RequestMapping("/api/v1.0/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public Collection<NoticeResposeTo> getAll() {
        return noticeService.getAll();
    }

    @GetMapping("/paged")
    public Page<NoticeResposeTo> getAllPaged(@PageableDefault(size = 20) Pageable pageable) {
        return noticeService.getAll(pageable);
    }

    @GetMapping("/tweet/{tweetId}")
    public Collection<NoticeResposeTo> getByTweetId(@PathVariable Long tweetId) {
        return noticeService.getByTweetId(tweetId);
    }

    @GetMapping("/tweet/{tweetId}/paged")
    public Page<NoticeResposeTo> getByTweetIdPaged(
            @PathVariable Long tweetId,
            @PageableDefault(size = 20) Pageable pageable) {
        return noticeService.getByTweetId(tweetId, pageable);
    }

    @GetMapping("/filter")
    public Page<NoticeResposeTo> filter(
            @RequestParam(required = false) Long tweetId,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 20) Pageable pageable) {

        NoticeFilter filter = NoticeFilter.builder()
                .tweetId(tweetId)
                .content(content)
                .build();

        return noticeService.getAll(filter, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeResposeTo create(@RequestBody @Valid NoticeRequestTo inputDto) {
        try {
            return noticeService.create(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NoticeResposeTo update(@RequestBody @Valid NoticeRequestTo inputDto) {
        try {
            return noticeService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public NoticeResposeTo read(@PathVariable long id) {
        try {
            return noticeService.get(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean deleted = noticeService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notice not found with id: " + id);
        }
    }
}