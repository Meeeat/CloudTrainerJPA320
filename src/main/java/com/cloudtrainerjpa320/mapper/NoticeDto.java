package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoticeDto {
    NoticeResposeTo out(Notice entity);

    Notice in(NoticeRequestTo inputDto);
}
