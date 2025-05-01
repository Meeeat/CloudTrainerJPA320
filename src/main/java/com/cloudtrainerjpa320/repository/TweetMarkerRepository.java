package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.TweetMarker;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetMarkerRepository extends BaseRepository<TweetMarker, Long> {
    List<TweetMarker> findByTweetId(Long tweetId);
    List<TweetMarker> findByMarkerId(Long markerId);
    void deleteByTweetIdAndMarkerId(Long tweetId, Long markerId);
}