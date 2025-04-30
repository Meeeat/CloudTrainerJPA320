package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.CreatorDto;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreatorService extends BaseService<Creator, CreatorRequestTo, CreatorResponseTo, Long> {

    private final CreatorDto mapper;

    public CreatorService(CreatorRepository repository, CreatorDto mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    protected CreatorResponseTo mapToResponse(Creator entity) {
        return mapper.out(entity);
    }

    @Override
    protected Creator mapToEntity(CreatorRequestTo dto) {
        return mapper.in(dto);
    }

    @Override
    protected Long getDtoId(CreatorRequestTo dto) {
        return dto.getId();
    }

    @Override
    protected void beforeCreate(Creator entity) {
        entity.setId(null);
    }

    @Override
    protected String getEntityName() {
        return "Creator";
    }
}