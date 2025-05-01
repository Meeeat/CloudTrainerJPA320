package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends BaseRepository<Notice, Long> {
    List<Notice> findByTweetId(Long tweetId);
    Page<Notice> findByTweetId(Long tweetId, Pageable pageable);
}