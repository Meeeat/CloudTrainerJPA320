package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.MarkerDto;
import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.repository.impl.MarkerRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MarkerService {

    public final MarkerRepoImpl repoImpl;
    public final MarkerDto mapper;

    public List<MarkerResponseTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public MarkerResponseTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public MarkerResponseTo create(MarkerRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public MarkerResponseTo update(MarkerRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
