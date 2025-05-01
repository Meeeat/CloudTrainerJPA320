package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.MarkerDto;
import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.repository.MarkerRepository;
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
public class MarkerService {

    private final MarkerRepository repository;
    private final MarkerDto mapper;

    public MarkerService(MarkerRepository repository, MarkerDto mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<MarkerResponseTo> getAll() {
        return repository.findAll().stream()
                .map(mapper::out)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MarkerResponseTo> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public Page<MarkerResponseTo> getAll(Specification<Marker> spec, Pageable pageable) {
        return repository.findAll(spec, pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public MarkerResponseTo get(Long id) {
        return repository.findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new NoSuchElementException("Marker not found with id: " + id));
    }

    @Transactional
    public MarkerResponseTo create(MarkerRequestTo inputDto) {
        Marker entity = mapper.in(inputDto);
        entity.setId(null);
        Marker savedEntity = repository.save(entity);
        return mapper.out(savedEntity);
    }

    @Transactional
    public MarkerResponseTo update(MarkerRequestTo inputDto) {
        if (inputDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        if (!repository.existsById(inputDto.getId())) {
            throw new NoSuchElementException("Marker not found with id: " + inputDto.getId());
        }

        Marker entity = mapper.in(inputDto);
        Marker updatedEntity = repository.save(entity);
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

    @Transactional(readOnly = true)
    public MarkerResponseTo getByName(String name) {
        return repository.findByName(name)
                .map(mapper::out)
                .orElseThrow(() -> new NoSuchElementException("Marker not found with name: " + name));
    }
}