package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.dto.request.CreatorRequestTo;
import com.cloudtrainerjpa320.dto.response.CreatorResponseTo;
import com.cloudtrainerjpa320.mapper.CreatorMapper;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.service.impl.CreatorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/creators")
public class CreatorController {

    private final CreatorServiceImpl creatorService;
    private final CreatorMapper creatorMapper;

    public CreatorController(CreatorServiceImpl creatorService, CreatorMapper creatorMapper) {
        this.creatorService = creatorService;
        this.creatorMapper = creatorMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatorResponseTo create(@Valid @RequestBody CreatorRequestTo requestTo) {
        Creator creator = creatorMapper.toEntity(requestTo);
        return creatorMapper.toDto(creatorService.create(creator));
    }

    @GetMapping("/{id}")
    public CreatorResponseTo getById(@PathVariable Long id) {
        return creatorMapper.toDto(creatorService.getById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreatorResponseTo> getAll() {
        return creatorService.getAll().stream()
                .map(creatorMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public CreatorResponseTo update(@PathVariable Long id, @Valid @RequestBody CreatorRequestTo requestTo) {
        Creator creator = creatorMapper.toEntity(requestTo);
        return creatorMapper.toDto(creatorService.update(id, creator));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        creatorService.deleteById(id);
    }

    @GetMapping("/search")
    public Page<CreatorResponseTo> search(@RequestParam(required = false) String login,
                                          @RequestParam(required = false) String name,
                                          Pageable pageable) {
        if (login != null) {
            return creatorService.findByLoginContaining(login, pageable).map(creatorMapper::toDto);
        } else if (name != null) {
            return creatorService.findByName(name, pageable).map(creatorMapper::toDto);
        }
        return creatorService.getAll(pageable).map(creatorMapper::toDto);
    }
}