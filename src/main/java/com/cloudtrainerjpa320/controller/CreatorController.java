package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.service.CreatorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<CreatorResponseTo> getAllPaged(Pageable pageable) {
        return creatorService.getAll(pageable);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public CreatorResponseTo read(@PathVariable long id) {
        return creatorService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = creatorService.delete(id);
        if (!delete) {
            throw new EntityNotFoundException("Creator not found with id: " + id);
        }
    }
}
