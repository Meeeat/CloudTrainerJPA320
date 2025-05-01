package com.cloudtrainerjpa320.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {

    T create(T entity);

    T getById(Long id);

    List<T> getAll();

    Page<T> getAll(Pageable pageable);

    T update(Long id, T entity);

    void deleteById(Long id);
}