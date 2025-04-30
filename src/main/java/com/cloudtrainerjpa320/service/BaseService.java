package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Базовый сервис для операций CRUD
 * @param <E> тип сущности
 * @param <D> тип DTO для запроса
 * @param <R> тип DTO для ответа
 * @param <ID> тип идентификатора
 */
public abstract class BaseService<E, D, R, ID> {

    protected final BaseRepository<E, ID> repository;

    public BaseService(BaseRepository<E, ID> repository) {
        this.repository = repository;
    }

    protected abstract R mapToResponse(E entity);

    protected abstract E mapToEntity(D dto);

    protected abstract ID getDtoId(D dto);

    @Transactional(readOnly = true)
    public List<R> getAll() {
        return repository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<R> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public R get(ID id) {
        return repository
                .findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName() + " not found with id: " + id));
    }

    @Transactional
    public R create(D dto) {
        E entity = mapToEntity(dto);
        this.beforeCreate(entity);
        return mapToResponse(repository.save(entity));
    }

    @Transactional
    public R update(D dto) {
        ID id = getDtoId(dto);
        if (id == null || !repository.existsById(id)) {
            throw new EntityNotFoundException(getEntityName() + " not found with id: " + id);
        }

        E entity = mapToEntity(dto);
        this.beforeUpdate(entity);
        return mapToResponse(repository.save(entity));
    }

    @Transactional
    public boolean delete(ID id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    protected void beforeCreate(E entity) {
    }

    protected void beforeUpdate(E entity) {
    }

    protected abstract String getEntityName();
}