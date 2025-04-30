package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.service.MarkerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1.0/markers")
public class MarkerController {

    private final MarkerService markerService;

    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping
    public Collection<MarkerResponseTo> getAll() {
        return markerService.getAll();
    }

    @GetMapping("/paged")
    public Page<MarkerResponseTo> getAllPaged(Pageable pageable) {
        return markerService.getAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo create(@RequestBody @Valid MarkerRequestTo inputDto) {
        return markerService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo update(@RequestBody @Valid MarkerRequestTo inputDto) {
        try {
            return markerService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public MarkerResponseTo read(@PathVariable long id) {
        return markerService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = markerService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
