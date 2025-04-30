package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.MarkerDto;
import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.repository.MarkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MarkerService extends BaseService<Marker, MarkerRequestTo, MarkerResponseTo, Long> {

    private final MarkerDto mapper;

    public MarkerService(MarkerRepository repository, MarkerDto mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    protected MarkerResponseTo mapToResponse(Marker entity) {
        return mapper.out(entity);
    }

    @Override
    protected Marker mapToEntity(MarkerRequestTo dto) {
        return mapper.in(dto);
    }

    @Override
    protected Long getDtoId(MarkerRequestTo dto) {
        return dto.getId();
    }

    @Override
    protected void beforeCreate(Marker entity) {
        entity.setId(null);
    }

    @Override
    protected String getEntityName() {
        return "Marker";
    }
}