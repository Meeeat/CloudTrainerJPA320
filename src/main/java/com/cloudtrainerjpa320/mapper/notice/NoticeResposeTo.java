package com.cloudtrainerjpa320.mapper.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResposeTo {

    Long id;
    Long tweetId;
    String content;
}
