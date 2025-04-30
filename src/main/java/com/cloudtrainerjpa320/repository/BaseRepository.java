package com.cloudtrainerjpa320.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaSpecificationExecutor<T> {

    Optional<T> findById(ID id);

    List<T> findAll();

    Page<T> FindAll(Pageable pageable);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    <S extends T> S save(S entity);

    void deleteById(ID id);

}