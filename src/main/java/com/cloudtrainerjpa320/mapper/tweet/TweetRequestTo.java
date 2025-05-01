package com.cloudtrainerjpa320.mapper.tweet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequestTo {

    @Positive
    Long id;

    @Positive
    Long creatorId;

    @NotBlank
    @Size(min = 2, max = 64)
    String title;

    @Size(min = 4, max = 2048)
    String content;

    LocalDateTime created;
    LocalDateTime modified;
}
