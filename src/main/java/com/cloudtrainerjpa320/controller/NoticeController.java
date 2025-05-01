package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.dto.request.NoticeRequestTo;
import com.cloudtrainerjpa320.dto.response.NoticeResponseTo;
import com.cloudtrainerjpa320.mapper.NoticeMapper;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.service.impl.NoticeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notices")
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final NoticeMapper noticeMapper;

    public NoticeController(NoticeServiceImpl noticeService, NoticeMapper noticeMapper) {
        this.noticeService = noticeService;
        this.noticeMapper = noticeMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeResponseTo create(@Valid @RequestBody NoticeRequestTo requestTo) {
        Notice notice = noticeMapper.toEntity(requestTo);
        return noticeMapper.toDto(noticeService.create(notice));
    }

    @GetMapping("/{id}")
    public NoticeResponseTo getById(@PathVariable Long id) {
        return noticeMapper.toDto(noticeService.getById(id));
    }

    @GetMapping
    public List<NoticeResponseTo> getAll() {
        return noticeService.getAll().stream()
                .map(noticeMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public NoticeResponseTo update(@PathVariable Long id, @Valid @RequestBody NoticeRequestTo requestTo) {
        Notice notice = noticeMapper.toEntity(requestTo);
        return noticeMapper.toDto(noticeService.update(id, notice));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        noticeService.deleteById(id);
    }

    @GetMapping("/tweet/{tweetId}")
    public Page<NoticeResponseTo> getByTweetId(@PathVariable Long tweetId, Pageable pageable) {
        return noticeService.findByTweetId(tweetId, pageable).map(noticeMapper::toDto);
    }
}