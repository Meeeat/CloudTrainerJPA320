package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.NoticeDto;
import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.NoticeRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoticeService extends BaseService<Notice, NoticeRequestTo, NoticeResposeTo, Long> {

    private final NoticeDto mapper;
    private final TweetRepository tweetRepository;

    public NoticeService(NoticeRepository repository, NoticeDto mapper, TweetRepository tweetRepository) {
        super(repository);
        this.mapper = mapper;
        this.tweetRepository = tweetRepository;
    }

    @Override
    protected NoticeResposeTo mapToResponse(Notice entity) {
        return mapper.out(entity);
    }

    @Override
    protected Notice mapToEntity(NoticeRequestTo dto) {
        return mapper.in(dto);
    }

    @Override
    protected Long getDtoId(NoticeRequestTo dto) {
        return dto.getId();
    }

    @Override
    protected void beforeCreate(Notice entity) {
        entity.setId(null);
        setTweet(entity);
    }

    @Override
    protected void beforeUpdate(Notice entity) {
        setTweet(entity);
    }

    @Override
    protected String getEntityName() {
        return "Notice";
    }

    private void setTweet(Notice entity) {
        Long tweetId = entity.getTweet() != null ? entity.getTweet().getId() : null;
        if (tweetId == null) {
            throw new EntityNotFoundException("Tweet ID must not be null");
        }

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new EntityNotFoundException("Tweet not found with id: " + tweetId));
        entity.setTweet(tweet);
    }
}