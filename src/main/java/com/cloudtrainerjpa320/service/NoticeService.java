package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.mapper.NoticeDto;
import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeResposeTo;
import com.cloudtrainerjpa320.repository.impl.NoticeRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticeService {

    public final NoticeRepoImpl repoImpl;
    public final NoticeDto mapper;

    public List<NoticeResposeTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public NoticeResposeTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public NoticeResposeTo create(NoticeRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public NoticeResposeTo update(NoticeRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
