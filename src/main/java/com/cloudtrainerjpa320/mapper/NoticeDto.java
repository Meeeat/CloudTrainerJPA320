package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NoticeDto {

    @Mapping(target = "tweetId", source = "tweet.id")
    NoticeResposeTo out(Notice entity);

    @Mapping(target = "tweet", source = "tweetId", qualifiedByName = "idToTweet")
    Notice in(NoticeRequestTo inputDto);

    @Named("idToTweet")
    default Tweet idToTweet(Long tweetId) {
        if (tweetId == null) {
            return null;
        }
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        return tweet;
    }
}