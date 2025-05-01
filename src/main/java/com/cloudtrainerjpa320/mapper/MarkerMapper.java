package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.dto.request.MarkerRequestTo;
import com.cloudtrainerjpa320.dto.response.MarkerResponseTo;
import com.cloudtrainerjpa320.model.Marker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarkerMapper {

    @Mapping(target = "id", ignore = true)
    Marker toEntity(MarkerRequestTo dto);

    MarkerResponseTo toDto(Marker entity);
}