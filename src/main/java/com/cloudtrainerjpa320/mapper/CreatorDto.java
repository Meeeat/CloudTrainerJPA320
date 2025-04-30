package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreatorDto {

    CreatorResponseTo out(Creator entity);

    @Mapping(target = "tweets", ignore = true)
    Creator in(CreatorRequestTo inputDto);
}