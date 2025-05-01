package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Marker;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkerRepository extends BaseRepository<Marker, Long> {
    Optional<Marker> findByName(String name);
}