package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.filter.CreatorFilter;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.service.CreatorService;
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
@RequestMapping("/api/v1.0/creators")
public class CreatorController {

    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @GetMapping
    public Collection<CreatorResponseTo> getAll() {
        return creatorService.getAll();
    }

    @GetMapping("/paged")
    public Page<CreatorResponseTo> getAllPaged(@PageableDefault(size = 20) Pageable pageable) {
        return creatorService.getAll(pageable);
    }

    @GetMapping("/filter")
    public Page<CreatorResponseTo> filter(
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @PageableDefault(size = 20) Pageable pageable) {

        CreatorFilter filter = CreatorFilter.builder()
                .login(login)
                .firstname(firstname)
                .lastname(lastname)
                .build();

        return creatorService.getAll(filter, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatorResponseTo create(@RequestBody @Valid CreatorRequestTo inputDto) {
        return creatorService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo update(@RequestBody @Valid CreatorRequestTo inputDto) {
        try {
            return creatorService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public CreatorResponseTo read(@PathVariable long id) {
        try {
            return creatorService.get(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean deleted = creatorService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Creator not found with id: " + id);
        }
    }
}