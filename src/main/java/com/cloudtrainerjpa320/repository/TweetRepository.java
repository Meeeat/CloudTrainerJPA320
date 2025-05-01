package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends BaseRepository<Tweet, Long> {
    List<Tweet> findByCreatorId(Long creatorId);
    Page<Tweet> findByCreatorId(Long creatorId, Pageable pageable);
}