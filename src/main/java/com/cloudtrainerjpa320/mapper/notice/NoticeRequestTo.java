package com.cloudtrainerjpa320.mapper.notice;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestTo {

    @Positive
    Long id;

    @Positive
    Long tweetId;

    @Size(min = 2, max = 2048)
    String content;
}
