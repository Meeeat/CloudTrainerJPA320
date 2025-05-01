package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.model.Marker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarkerDto {
    @Mapping(target = "tweets", ignore = true)
    Marker in(MarkerRequestTo inputDto);

    MarkerResponseTo out(Marker entity);
}