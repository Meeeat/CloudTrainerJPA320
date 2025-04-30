package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.NoticeDto;
import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.repository.NoticeRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository repository;
    private final TweetRepository tweetRepository;
    private final NoticeDto mapper;

    @Transactional(readOnly = true)
    public List<NoticeResposeTo> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<NoticeResposeTo> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public NoticeResposeTo get(Long id) {
        return repository
                .findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new EntityNotFoundException("get Notice not found with id: " + id));
    }

    @Transactional
    public NoticeResposeTo create(NoticeRequestTo input) {
        Notice entity = mapper.in(input);
        entity.setId(null);

        entity.setTweet(tweetRepository.findById(input.getTweetId())
                .orElseThrow(() -> new EntityNotFoundException("create Tweet in Notice not found with id: " + input.getTweetId())));

        return mapper.out(repository.save(entity));
    }

    @Transactional
    public NoticeResposeTo update(NoticeRequestTo input) {
        if (input.getId() == null || !repository.existsById(input.getId())) {
            throw new EntityNotFoundException("update Notice not found with id: " + input.getId());
        }

        Notice entity = mapper.in(input);

        entity.setTweet(tweetRepository.findById(input.getTweetId())
                .orElseThrow(() -> new EntityNotFoundException("update Tweet in Notice not found with id: " + input.getTweetId())));

        return mapper.out(repository.save(entity));
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}