package com.cloudtrainerjpa320.service.impl;

import com.cloudtrainerjpa320.exception.DuplicateLoginException;
import com.cloudtrainerjpa320.exception.ResourceNotFoundException;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import com.cloudtrainerjpa320.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreatorServiceImpl implements BaseService<Creator> {

    private final CreatorRepository creatorRepository;

    public CreatorServiceImpl(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    @Override
    public Creator create(Creator creator) {
        if (creatorRepository.existsByLogin(creator.getLogin())) {
            throw new DuplicateLoginException(creator.getLogin());
        }
        return creatorRepository.save(creator);
    }

    @Override
    @Transactional(readOnly = true)
    public Creator getById(Long id) {
        return creatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Creator> getAll() {
        return creatorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Creator> getAll(Pageable pageable) {
        return creatorRepository.findAll(pageable);
    }

    @Override
    public Creator update(Long id, Creator creator) {
        if (!creatorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Creator with ID " + id + " not found");
        }
        creator.setId(id);
        return creatorRepository.save(creator);
    }

    @Override
    public void deleteById(Long id) {
        if (!creatorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Creator with ID " + id + " not found");
        }
        creatorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Creator> findByLoginContaining(String login, Pageable pageable) {
        return creatorRepository.findByLoginContaining(login, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Creator> findByName(String name, Pageable pageable) {
        return creatorRepository.findByFirstnameContainingOrLastnameContaining(name, name, pageable);
    }
}