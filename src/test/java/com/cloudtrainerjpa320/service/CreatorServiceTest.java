package com.cloudtrainerjpa320.service;

import com.cloudtrainerjpa320.exception.EntityNotFoundException;
import com.cloudtrainerjpa320.mapper.CreatorDto;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.creator.CreatorResponseTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetResponseTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatorServiceTest {

    @Mock
    private CreatorRepository creatorRepository;

    @Mock
    private CreatorDto creatorDto;

    @InjectMocks
    private CreatorService creatorService;

    @Test
    void getAllCreatorsTest() {
        // Подготовка данных
        Creator creator1 = Creator.builder()
                .id(1L)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        Creator creator2 = Creator.builder()
                .id(2L)
                .login("user2")
                .password("pass2")
                .firstname("First2")
                .lastname("Last2")
                .build();

        CreatorResponseTo response1 = CreatorResponseTo.builder()
                .id(1L)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        CreatorResponseTo response2 = CreatorResponseTo.builder()
                .id(2L)
                .login("user2")
                .password("pass2")
                .firstname("First2")
                .lastname("Last2")
                .build();

        // Настройка моков
        when(creatorRepository.findAll()).thenReturn(List.of(creator1, creator2));
        when(creatorDto.out(creator1)).thenReturn(response1);
        when(creatorDto.out(creator2)).thenReturn(response2);

        // Выполнение метода
        List<CreatorResponseTo> result = creatorService.getAll();

        // Проверка результатов
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        verify(creatorRepository, times(1)).findAll();
        verify(creatorDto, times(2)).out(any(Creator.class));
    }

    @Test
    void getAllCreatorsPagedTest() {
        // Подготовка данных
        Creator creator1 = Creator.builder()
                .id(1L)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        Creator creator2 = Creator.builder()
                .id(2L)
                .login("user2")
                .password("pass2")
                .firstname("First2")
                .lastname("Last2")
                .build();

        CreatorResponseTo response1 = CreatorResponseTo.builder()
                .id(1L)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        CreatorResponseTo response2 = CreatorResponseTo.builder()
                .id(2L)
                .login("user2")
                .password("pass2")
                .firstname("First2")
                .lastname("Last2")
                .build();

        Page<Creator> creatorPage = new PageImpl<>(List.of(creator1, creator2));
        Pageable pageable = PageRequest.of(0, 10);

        // Настройка моков
        when(creatorRepository.findAll(pageable)).thenReturn(creatorPage);
        when(creatorDto.out(creator1)).thenReturn(response1);
        when(creatorDto.out(creator2)).thenReturn(response2);

        // Выполнение метода
        Page<CreatorResponseTo> result = creatorService.getAll(pageable);

        // Проверка результатов
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(1).getId()).isEqualTo(2L);
        verify(creatorRepository, times(1)).findAll(pageable);
        verify(creatorDto, times(2)).out(any(Creator.class));
    }

    @Test
    void getCreatorByIdTest() {
        // Подготовка данных
        Long id = 1L;
        Creator creator = Creator.builder()
                .id(id)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        CreatorResponseTo response = CreatorResponseTo.builder()
                .id(id)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        // Настройка моков
        when(creatorRepository.findById(id)).thenReturn(Optional.of(creator));
        when(creatorDto.out(creator)).thenReturn(response);

        // Выполнение метода
        CreatorResponseTo result = creatorService.get(id);

        // Проверка результатов
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getLogin()).isEqualTo("user1");
        verify(creatorRepository, times(1)).findById(id);
        verify(creatorDto, times(1)).out(creator);
    }

    @Test
    void getCreatorByIdNotFoundTest() {
        // Подготовка данных
        Long id = 999L;

        // Настройка моков
        when(creatorRepository.findById(id)).thenReturn(Optional.empty());

        // Выполнение метода и проверка исключения
        assertThrows(EntityNotFoundException.class, () -> creatorService.get(id));
        verify(creatorRepository, times(1)).findById(id);
        verify(creatorDto, never()).out(any(Creator.class));
    }

    @Test
    void createCreatorTest() {
        // Подготовка данных
        CreatorRequestTo requestTo = CreatorRequestTo.builder()
                .login("newuser")
                .password("newpass")
                .firstname("NewFirst")
                .lastname("NewLast")
                .build();

        Creator creator = Creator.builder()
                .login("newuser")
                .password("newpass")
                .firstname("NewFirst")
                .lastname("NewLast")
                .build();

        Creator savedCreator = Creator.builder()
                .id(1L)
                .login("newuser")
                .password("newpass")
                .firstname("NewFirst")
                .lastname("NewLast")
                .build();

        CreatorResponseTo response = CreatorResponseTo.builder()
                .id(1L)
                .login("newuser")
                .password("newpass")
                .firstname("NewFirst")
                .lastname("NewLast")
                .build();

        // Настройка моков
        when(creatorDto.in(requestTo)).thenReturn(creator);
        when(creatorRepository.save(creator)).thenReturn(savedCreator);
        when(creatorDto.out(savedCreator)).thenReturn(response);

        // Выполнение метода
        CreatorResponseTo result = creatorService.create(requestTo);

        // Проверка результатов
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLogin()).isEqualTo("newuser");
        verify(creatorDto, times(1)).in(requestTo);
        verify(creatorRepository, times(1)).save(creator);
        verify(creatorDto, times(1)).out(savedCreator);
    }

    @Test
    void updateCreatorTest() {
        // Подготовка данных
        Long id = 1L;
        CreatorRequestTo requestTo = CreatorRequestTo.builder()
                .id(id)
                .login("updateduser")
                .password("updatedpass")
                .firstname("UpdatedFirst")
                .lastname("UpdatedLast")
                .build();

        Creator creator = Creator.builder()
                .id(id)
                .login("updateduser")
                .password("updatedpass")
                .firstname("UpdatedFirst")
                .lastname("UpdatedLast")
                .build();

        Creator updatedCreator = Creator.builder()
                .id(id)
                .login("updateduser")
                .password("updatedpass")
                .firstname("UpdatedFirst")
                .lastname("UpdatedLast")
                .build();

        CreatorResponseTo response = CreatorResponseTo.builder()
                .id(id)
                .login("updateduser")
                .password("updatedpass")
                .firstname("UpdatedFirst")
                .lastname("UpdatedLast")
                .build();

        // Настройка моков
        when(creatorRepository.existsById(id)).thenReturn(true);
        when(creatorDto.in(requestTo)).thenReturn(creator);
        when(creatorRepository.save(creator)).thenReturn(updatedCreator);
        when(creatorDto.out(updatedCreator)).thenReturn(response);

        // Выполнение метода
        CreatorResponseTo result = creatorService.update(requestTo);

        // Проверка результатов
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getLogin()).isEqualTo("updateduser");
        verify(creatorRepository, times(1)).existsById(id);
        verify(creatorDto, times(1)).in(requestTo);
        verify(creatorRepository, times(1)).save(creator);
        verify(creatorDto, times(1)).out(updatedCreator);
    }

    @Test
    void updateCreatorNotFoundTest() {
        // Подготовка данных
        Long id = 999L;
        CreatorRequestTo requestTo = CreatorRequestTo.builder()
                .id(id)
                .login("updateduser")
                .password("updatedpass")
                .firstname("UpdatedFirst")
                .lastname("UpdatedLast")
                .build();

        // Настройка моков
        when(creatorRepository.existsById(id)).thenReturn(false);

        // Выполнение метода и проверка исключения
        assertThrows(EntityNotFoundException.class, () -> creatorService.update(requestTo));
        verify(creatorRepository, times(1)).existsById(id);
        verify(creatorDto, never()).in(any(CreatorRequestTo.class));
        verify(creatorRepository, never()).save(any(Creator.class));
    }

    @Test
    void deleteCreatorTest() {
        // Подготовка данных
        Long id = 1L;

        // Настройка моков
        when(creatorRepository.existsById(id)).thenReturn(true);
        doNothing().when(creatorRepository).deleteById(id);

        // Выполнение метода
        boolean result = creatorService.delete(id);

        // Проверка результатов
        assertThat(result).isTrue();
        verify(creatorRepository, times(1)).existsById(id);
        verify(creatorRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteCreatorNotFoundTest() {
        // Подготовка данных
        Long id = 999L;

        // Настройка моков
        when(creatorRepository.existsById(id)).thenReturn(false);

        // Выполнение метода
        boolean result = creatorService.delete(id);

        // Проверка результатов
        assertThat(result).isFalse();
        verify(creatorRepository, times(1)).existsById(id);
        verify(creatorRepository, never()).deleteById(any());
    }
}

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private CreatorRepository creatorRepository;

    @Mock
    private com.cloudtrainerjpa320.mapper.TweetDto tweetDto;

    @InjectMocks
    private TweetService tweetService;

    @Test
    void createTweetTest() {
        // Подготовка данных
        Long creatorId = 1L;
        Creator creator = Creator.builder()
                .id(creatorId)
                .login("user1")
                .password("pass1")
                .firstname("First1")
                .lastname("Last1")
                .build();

        TweetRequestTo requestTo = TweetRequestTo.builder()
                .creatorId(creatorId)
                .title("Test Tweet")
                .content("Test Content")
                .build();

        Tweet tweet = Tweet.builder()
                .creator(creator)
                .title("Test Tweet")
                .content("Test Content")
                .build();

        Tweet savedTweet = Tweet.builder()
                .id(1L)
                .creator(creator)
                .title("Test Tweet")
                .content("Test Content")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        TweetResponseTo response = TweetResponseTo.builder()
                .id(1L)
                .creatorId(creatorId)
                .title("Test Tweet")
                .content("Test Content")
                .created(savedTweet.getCreated())
                .modified(savedTweet.getModified())
                .build();

        // Настройка моков
        when(tweetDto.in(requestTo)).thenReturn(tweet);
        when(creatorRepository.findById(creatorId)).thenReturn(Optional.of(creator));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(savedTweet);
        when(tweetDto.out(savedTweet)).thenReturn(response);

        // Выполнение метода
        TweetResponseTo result = tweetService.create(requestTo);

        // Проверка результатов
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCreatorId()).isEqualTo(creatorId);
        assertThat(result.getTitle()).isEqualTo("Test Tweet");
        verify(tweetDto, times(1)).in(requestTo);
        verify(creatorRepository, times(1)).findById(creatorId);
        verify(tweetRepository, times(1)).save(any(Tweet.class));
        verify(tweetDto, times(1)).out(savedTweet);
    }

    @Test
    void createTweetWithInvalidCreatorTest() {
        // Подготовка данных
        Long invalidCreatorId = 999L;

        TweetRequestTo requestTo = TweetRequestTo.builder()
                .creatorId(invalidCreatorId)
                .title("Test Tweet")
                .content("Test Content")
                .build();

        Tweet tweet = Tweet.builder()
                .creator(Creator.builder().id(invalidCreatorId).build())
                .title("Test Tweet")
                .content("Test Content")
                .build();

        // Настройка моков
        when(tweetDto.in(requestTo)).thenReturn(tweet);
        when(creatorRepository.findById(invalidCreatorId)).thenReturn(Optional.empty());

        // Выполнение метода и проверка исключения
        assertThrows(EntityNotFoundException.class, () -> tweetService.create(requestTo));
        verify(tweetDto, times(1)).in(requestTo);
        verify(creatorRepository, times(1)).findById(invalidCreatorId);
        verify(tweetRepository, never()).save(any(Tweet.class));
    }
}