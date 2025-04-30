package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetDto {

    @Mapping(target = "creatorId", source = "creator.id")
    TweetResponseTo out(Tweet entity);

    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "notices", ignore = true)
    @Mapping(target = "markers", ignore = true)
    Tweet in(TweetRequestTo inputDto);
}
