package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.TweetDto;
import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TweetService extends BaseService<Tweet, TweetRequestTo, TweetResponseTo, Long> {

    private final TweetDto mapper;
    private final CreatorRepository creatorRepository;

    public TweetService(TweetRepository repository, TweetDto mapper, CreatorRepository creatorRepository) {
        super(repository);
        this.mapper = mapper;
        this.creatorRepository = creatorRepository;
    }

    @Override
    protected TweetResponseTo mapToResponse(Tweet entity) {
        return mapper.out(entity);
    }

    @Override
    protected Tweet mapToEntity(TweetRequestTo dto) {
        return mapper.in(dto);
    }

    @Override
    protected Long getDtoId(TweetRequestTo dto) {
        return dto.getId();
    }

    @Override
    protected void beforeCreate(Tweet entity) {
        entity.setId(null);
        setCreatorAndTimestamps(entity, true);
    }

    @Override
    protected void beforeUpdate(Tweet entity) {
        setCreatorAndTimestamps(entity, false);
    }

    @Override
    protected String getEntityName() {
        return "Tweet";
    }

    private void setCreatorAndTimestamps(Tweet entity, boolean isCreate) {
        if (entity.getCreator() != null && entity.getCreator().getId() != null) {
            Long creatorId = entity.getCreator().getId();
            entity.setCreator(creatorRepository.findById(creatorId)
                    .orElseThrow(() -> new EntityNotFoundException("Creator not found with id: " + creatorId)));
        } else {
            throw new EntityNotFoundException("Creator ID must not be null");
        }

        if (isCreate) {
            if (entity.getCreated() == null) {
                entity.setCreated(LocalDateTime.now());
            }
        } else {
            entity.setModified(LocalDateTime.now());
        }
    }
}