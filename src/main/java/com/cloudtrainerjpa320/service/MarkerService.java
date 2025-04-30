package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.MarkerDto;
import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final MarkerRepository repository;
    private final MarkerDto mapper;

    @Transactional(readOnly = true)
    public List<MarkerResponseTo> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<MarkerResponseTo> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public MarkerResponseTo get(Long id) {
        return repository
                .findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new EntityNotFoundException("get Marker not found with id: " + id));
    }

    @Transactional
    public MarkerResponseTo create(MarkerRequestTo input) {
        Marker entity = mapper.in(input);
        entity.setId(null);
        return mapper.out(repository.save(entity));
    }

    @Transactional
    public MarkerResponseTo update(MarkerRequestTo input) {
        if (input.getId() == null || !repository.existsById(input.getId())) {
            throw new EntityNotFoundException("update Marker not found with id: " + input.getId());
        }

        Marker entity = mapper.in(input);
        return mapper.out(repository.save(entity));
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}