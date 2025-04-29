package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.TweetDto;
import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.repository.impl.TweetRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TweetService {

    public final TweetRepoImpl repoImpl;
    public final TweetDto mapper;

    public List<TweetResponseTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public TweetResponseTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public TweetResponseTo create(TweetRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public TweetResponseTo update(TweetRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
