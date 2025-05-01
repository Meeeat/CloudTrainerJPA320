package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.dto.request.NoticeRequestTo;
import com.cloudtrainerjpa320.dto.response.NoticeResponseTo;
import com.cloudtrainerjpa320.model.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = TweetMapper.class)
public interface NoticeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tweet", source = "tweetId")
    Notice toEntity(NoticeRequestTo requestTo);

    @Mapping(target = "tweetId", source = "tweet.id")
    NoticeResponseTo toDto(Notice notice);
}