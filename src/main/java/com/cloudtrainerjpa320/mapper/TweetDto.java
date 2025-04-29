package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Tweet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TweetDto {
    TweetResponseTo out(Tweet entity);

    Tweet in(TweetRequestTo inputDto);
}
