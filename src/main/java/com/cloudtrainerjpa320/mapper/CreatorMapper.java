package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.dto.request.CreatorRequestTo;
import com.cloudtrainerjpa320.dto.response.CreatorResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreatorMapper {

    @Mapping(target = "id", ignore = true)
    Creator toEntity(CreatorRequestTo dto);

    @Mapping(target = "id", source = "id")
    CreatorResponseTo toDto(Creator creator);

    default Creator map(Long creatorId) {
        if (creatorId == null) return null;
        Creator creator = new Creator();
        creator.setId(creatorId);
        return creator;
    }
}