package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkerRepository extends BaseRepository<Marker, Long> {

    Page<Marker> findByNameContaining(String name, Pageable pageable);

    Page<Marker> findByTweetsId(Long tweetId, Pageable pageable);

    Optional<Marker> findByName(String name);
}