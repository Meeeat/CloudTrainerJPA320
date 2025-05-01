package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends BaseRepository<Notice, Long> {

    Page<Notice> findByTweetId(Long tweetId, Pageable pageable);

    Page<Notice> findByContentContaining(String content, Pageable pageable);
}