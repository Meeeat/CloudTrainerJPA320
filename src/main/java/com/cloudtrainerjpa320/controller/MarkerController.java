package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.dto.request.MarkerRequestTo;
import com.cloudtrainerjpa320.dto.response.MarkerResponseTo;
import com.cloudtrainerjpa320.mapper.MarkerMapper;
import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.service.impl.MarkerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/markers")
public class MarkerController {

    private final MarkerServiceImpl markerService;
    private final MarkerMapper markerMapper;

    public MarkerController(MarkerServiceImpl markerService, MarkerMapper markerMapper) {
        this.markerService = markerService;
        this.markerMapper = markerMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo create(@Valid @RequestBody MarkerRequestTo requestTo) {
        Marker marker = markerMapper.toEntity(requestTo);
        return markerMapper.toDto(markerService.create(marker));
    }

    @GetMapping("/{id}")
    public MarkerResponseTo getById(@PathVariable Long id) {
        return markerMapper.toDto(markerService.getById(id));
    }

    @GetMapping
    public List<MarkerResponseTo> getAll() {
        return markerService.getAll().stream()
                .map(markerMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public MarkerResponseTo update(@PathVariable Long id, @Valid @RequestBody MarkerRequestTo requestTo) {
        Marker marker = markerMapper.toEntity(requestTo);
        return markerMapper.toDto(markerService.update(id, marker));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        markerService.deleteById(id);
    }

    @GetMapping("/search")
    public Page<MarkerResponseTo> search(@RequestParam String name, Pageable pageable) {
        return markerService.findByNameContaining(name, pageable).map(markerMapper::toDto);
    }
}