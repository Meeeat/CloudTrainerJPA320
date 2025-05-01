package com.cloudtrainerjpa320.service.impl;

import com.cloudtrainerjpa320.exception.ResourceNotFoundException;
import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.repository.MarkerRepository;
import com.cloudtrainerjpa320.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MarkerServiceImpl implements BaseService<Marker> {

    private final MarkerRepository markerRepository;

    public MarkerServiceImpl(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    @Override
    public Marker create(Marker marker) {
        return markerRepository.save(marker);
    }

    @Override
    @Transactional(readOnly = true)
    public Marker getById(Long id) {
        return markerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marker with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marker> getAll() {
        return markerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Marker> getAll(Pageable pageable) {
        return markerRepository.findAll(pageable);
    }

    @Override
    public Marker update(Long id, Marker marker) {
        if (!markerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Marker with ID " + id + " not found");
        }
        marker.setId(id);
        return markerRepository.save(marker);
    }

    @Override
    public void deleteById(Long id) {
        if (!markerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Marker with ID " + id + " not found");
        }
        markerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Marker> findByNameContaining(String name, Pageable pageable) {
        return markerRepository.findByNameContaining(name, pageable);
    }
}