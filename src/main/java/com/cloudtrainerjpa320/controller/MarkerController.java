package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.service.MarkerService;
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
    public Page<MarkerResponseTo> getAllPaged(@PageableDefault(size = 20) Pageable pageable) {
        return markerService.getAll(pageable);
    }

    @GetMapping("/name/{name}")
    public MarkerResponseTo getByName(@PathVariable String name) {
        try {
            return markerService.getByName(name);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public MarkerResponseTo read(@PathVariable long id) {
        try {
            return markerService.get(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean deleted = markerService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marker not found with id: " + id);
        }
    }
}