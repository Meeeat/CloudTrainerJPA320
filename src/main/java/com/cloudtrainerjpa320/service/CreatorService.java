package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.CreatorDto;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorService {

    public final CreatorRepository repository;
    public final CreatorDto mapper;

    @Transactional(readOnly = true)
    public List<CreatorResponseTo> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<CreatorResponseTo> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public CreatorResponseTo get(Long id) {
        return repository
                .findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new EntityNotFoundException("get Creator not found with id: " + id));
    }

    @Transactional
    public CreatorResponseTo create(CreatorRequestTo input) {
        Creator entity = mapper.in(input);
        entity.setId(null);
        return mapper.out(repository.save(entity));
    }

    @Transactional
    public CreatorResponseTo update(CreatorRequestTo input) {
        if (input.getId() == null || !repository.existsById(input.getId())) {
            throw new EntityNotFoundException("update Creator not found with id: " + input.getId());
        }
        Creator entity = mapper.in(input);
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
