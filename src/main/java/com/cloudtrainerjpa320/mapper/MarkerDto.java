package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerResponseTo;
import com.cloudtrainerjpa320.model.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerDto {
    MarkerResponseTo out(Marker entity);

    Marker in(MarkerRequestTo inputDto);
}
