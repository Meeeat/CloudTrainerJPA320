package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.CreatorDto;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.repository.impl.CreatorRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreatorService {

    public final CreatorRepoImpl repoImpl;
    public final CreatorDto mapper;

    public List<CreatorResponseTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public CreatorResponseTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public CreatorResponseTo create(CreatorRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public CreatorResponseTo update(CreatorRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
