package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Tweet;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends BaseRepository<Tweet, Long> {
}