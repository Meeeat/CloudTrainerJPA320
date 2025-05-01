package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.dto.request.TweetRequestTo;
import com.cloudtrainerjpa320.dto.response.TweetResponseTo;
import com.cloudtrainerjpa320.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface TweetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "creator", source = "creatorId")
    Tweet toEntity(TweetRequestTo requestTo);

    @Mapping(target = "creatorId", source = "creator.id")
    TweetResponseTo toDto(Tweet tweet);

    default Tweet map(Long tweetId) {
        if (tweetId == null) return null;
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        return tweet;
    }
}