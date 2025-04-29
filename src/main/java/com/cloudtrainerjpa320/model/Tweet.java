package com.cloudtrainerjpa320.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    Long id;
    Long creatorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;

}