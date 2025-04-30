package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Notice;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends BaseRepository<Notice, Long> {
}