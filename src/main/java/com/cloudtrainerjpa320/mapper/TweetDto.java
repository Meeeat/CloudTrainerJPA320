package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TweetDto {

    @Mapping(target = "creatorId", source = "creator.id")
    TweetResponseTo out(Tweet entity);

    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "idToCreator")
    @Mapping(target = "notices", ignore = true)
    @Mapping(target = "markers", ignore = true)
    Tweet in(TweetRequestTo inputDto);

    @Named("idToCreator")
    default Creator idToCreator(Long creatorId) {
        if (creatorId == null) {
            return null;
        }
        Creator creator = new Creator();
        creator.setId(creatorId);
        return creator;
    }
}