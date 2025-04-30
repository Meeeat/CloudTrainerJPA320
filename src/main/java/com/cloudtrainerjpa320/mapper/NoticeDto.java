package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoticeDto {

    @Mapping(target = "tweetId", source = "tweet.id")
    NoticeResposeTo out(Notice entity);

    @Mapping(target = "tweet", ignore = true)
    Notice in(NoticeRequestTo inputDto);
}
