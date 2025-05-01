package com.cloudtrainerjpa320.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @Override
    List<T> findAll();

    @Override
    Page<T> findAll(Pageable pageable);

    @Override
    Page<T> findAll(Specification<T> spec, Pageable pageable);

    @Override
    List<T> findAll(Specification<T> spec);
}