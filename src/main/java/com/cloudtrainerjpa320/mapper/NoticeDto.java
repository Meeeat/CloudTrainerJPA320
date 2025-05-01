package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.TweetRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@Mapper(componentModel = "spring", uses = {})
public abstract class NoticeDto {

    @Autowired
    protected TweetRepository tweetRepository;

    @Mapping(target = "tweet", source = "tweetId", qualifiedByName = "mapTweet")
    public abstract Notice in(NoticeRequestTo inputDto);

    @Mapping(target = "tweetId", source = "tweet.id")
    public abstract NoticeResposeTo out(Notice entity);

    @Named("mapTweet")
    protected Tweet mapTweet(Long tweetId) {
        if (tweetId == null) {
            return null;
        }
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NoSuchElementException("Tweet not found with id: " + tweetId));
    }
}