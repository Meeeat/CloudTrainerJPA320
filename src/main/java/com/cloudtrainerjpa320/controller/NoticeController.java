package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<NoticeResposeTo> getAllPaged(Pageable pageable) {
        return noticeService.getAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeResposeTo create(@RequestBody @Valid NoticeRequestTo inputDto) {
        return noticeService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NoticeResposeTo update(@RequestBody @Valid NoticeRequestTo inputDto) {
        try {
            return noticeService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public NoticeResposeTo read(@PathVariable long id) {
        return noticeService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = noticeService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
