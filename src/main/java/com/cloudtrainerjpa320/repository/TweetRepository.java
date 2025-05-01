package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TweetRepository extends BaseRepository<Tweet, Long> {

    Page<Tweet> findByCreator(Creator creator, Pageable pageable);

    Page<Tweet> findByCreatorId(Long creatorId, Pageable pageable);

    Page<Tweet> findByTitleContaining(String title, Pageable pageable);

    Page<Tweet> findByContentContaining(String content, Pageable pageable);

    Page<Tweet> findByCreatedBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    boolean existsByTitle(String title);
}