package com.cloudtrainerjpa320.service.impl;

import com.cloudtrainerjpa320.exception.ResourceNotFoundException;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.repository.NoticeRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import com.cloudtrainerjpa320.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeServiceImpl implements BaseService<Notice> {

    private final NoticeRepository noticeRepository;
    private final TweetRepository tweetRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository, TweetRepository tweetRepository) {
        this.noticeRepository = noticeRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Notice create(Notice notice) {
        if (notice.getTweet() != null && notice.getTweet().getId() != null) {
            tweetRepository.findById(notice.getTweet().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tweet with ID " + notice.getTweet().getId() + " not found"));
        }
        return noticeRepository.save(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public Notice getById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notice> getAll() {
        return noticeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notice> getAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Notice update(Long id, Notice notice) {
        if (!noticeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notice with ID " + id + " not found");
        }

        if (notice.getTweet() != null && notice.getTweet().getId() != null) {
            tweetRepository.findById(notice.getTweet().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tweet with ID " + notice.getTweet().getId() + " not found"));
        }

        notice.setId(id);
        return noticeRepository.save(notice);
    }

    @Override
    public void deleteById(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notice with ID " + id + " not found");
        }
        noticeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Notice> findByTweetId(Long tweetId, Pageable pageable) {
        return noticeRepository.findByTweetId(tweetId, pageable);
    }
}