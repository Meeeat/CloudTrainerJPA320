package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.NoticeDto;
import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository repository;
    private final NoticeDto mapper;

    public NoticeService(NoticeRepository repository, NoticeDto mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<NoticeResposeTo> getAll() {
        return repository.findAll().stream()
                .map(mapper::out)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<NoticeResposeTo> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public Page<NoticeResposeTo> getAll(Specification<Notice> spec, Pageable pageable) {
        return repository.findAll(spec, pageable)
                .map(mapper::out);
    }

    @Transactional(readOnly = true)
    public NoticeResposeTo get(Long id) {
        return repository.findById(id)
                .map(mapper::out)
                .orElseThrow(() -> new NoSuchElementException("Notice not found with id: " + id));
    }

    @Transactional
    public NoticeResposeTo create(NoticeRequestTo inputDto) {
        Notice entity = mapper.in(inputDto);
        entity.setId(null);
        Notice savedEntity = repository.save(entity);
        return mapper.out(savedEntity);
    }

    @Transactional
    public NoticeResposeTo update(NoticeRequestTo inputDto) {
        if (inputDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        if (!repository.existsById(inputDto.getId())) {
            throw new NoSuchElementException("Notice not found with id: " + inputDto.getId());
        }

        Notice entity = mapper.in(inputDto);
        Notice updatedEntity = repository.save(entity);
        return mapper.out(updatedEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<NoticeResposeTo> getByTweetId(Long tweetId) {
        return repository.findByTweetId(tweetId).stream()
                .map(mapper::out)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<NoticeResposeTo> getByTweetId(Long tweetId, Pageable pageable) {
        return repository.findByTweetId(tweetId, pageable)
                .map(mapper::out);
    }
}