package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Creator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends BaseRepository<Creator, Long> {

    Page<Creator> findByLoginContaining(String login, Pageable pageable);

    Page<Creator> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname, Pageable pageable);

    boolean existsByLogin(String login);
}