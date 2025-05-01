package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.CreatorDto;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreatorService {

    private final CreatorRepository repository;
    private final CreatorDto mapper;

    public CreatorService(CreatorRepository repository, CreatorDto mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CreatorResponseTo> getAll() {
        return repository.findAll().stream()
                .map(mapper::out)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CreatorResponseTo> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public Page<CreatorResponseTo> getAll(Specification<Creator> spec, Pageable pageable) {
        return repository.findAll(spec, pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public CreatorResponseTo get(Long id) {
        return repository.findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new NoSuchElementException("Creator not found with id: " + id));
    }

    @Transactional
    public CreatorResponseTo create(CreatorRequestTo inputDto) {
        Creator entity = mapper.in(inputDto);
        entity.setId(null);
        Creator savedEntity = repository.save(entity);
        return mapper.out(savedEntity);
    }

    @Transactional
    public CreatorResponseTo update(CreatorRequestTo inputDto) {
        if (inputDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        if (!repository.existsById(inputDto.getId())) {
            throw new NoSuchElementException("Creator not found with id: " + inputDto.getId());
        }

        Creator entity = mapper.in(inputDto);
        Creator updatedEntity = repository.save(entity);
        return mapper.out(updatedEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}