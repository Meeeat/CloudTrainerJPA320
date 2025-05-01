package com.cloudtrainerjpa320.mapper;

import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@Mapper(componentModel = "spring", uses = {})
public abstract class TweetDto {

    @Autowired
    protected CreatorRepository creatorRepository;

    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "mapCreator")
    @Mapping(target = "notices", ignore = true)
    @Mapping(target = "markers", ignore = true)
    public abstract Tweet in(TweetRequestTo inputDto);

    @Mapping(target = "creatorId", source = "creator.id")
    public abstract TweetResponseTo out(Tweet entity);

    @Named("mapCreator")
    protected Creator mapCreator(Long creatorId) {
        if (creatorId == null) {
            return null;
        }
        return creatorRepository.findById(creatorId)
                .orElseThrow(() -> new NoSuchElementException("Creator not found with id: " + creatorId));
    }
}