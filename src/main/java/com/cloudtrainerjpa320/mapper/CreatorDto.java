package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorDto {
    CreatorResponseTo out(Creator entity);

    Creator in(CreatorRequestTo inputDto);
}
