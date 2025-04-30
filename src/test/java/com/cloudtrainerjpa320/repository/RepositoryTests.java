package com.cloudtrainerjpa320.repository;

import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.model.Marker;
import com.cloudtrainerjpa320.model.Notice;
import com.cloudtrainerjpa320.model.Tweet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("init-schema.sql");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.liquibase.enabled", () -> "false"); // Отключаем Liquibase, чтобы избежать конфликтов
    }

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private MarkerRepository markerRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void creatorRepositoryTest() {
        // Создаем и сохраняем создателя
        Creator creator = Creator.builder()
                .login("testuser")
                .password("password123")
                .firstname("Test")
                .lastname("User")
                .build();

        Creator savedCreator = creatorRepository.save(creator);

        // Проверяем, что создатель сохранился
        assertThat(savedCreator.getId()).isNotNull();

        // Проверяем поиск по ID
        Optional<Creator> foundCreator = creatorRepository.findById(savedCreator.getId());
        assertThat(foundCreator).isPresent();
        assertThat(foundCreator.get().getLogin()).isEqualTo("testuser");

        // Проверяем получение всех создателей
        List<Creator> creators = creatorRepository.findAll();
        assertThat(creators).hasSize(1);

        // Проверяем пагинацию и сортировку
        Page<Creator> creatorPage = creatorRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "lastname")));
        assertThat(creatorPage.getContent()).hasSize(1);

        // Проверяем обновление
        creator.setFirstname("Updated");
        Creator updatedCreator = creatorRepository.save(creator);
        assertThat(updatedCreator.getFirstname()).isEqualTo("Updated");

        // Проверяем удаление
        creatorRepository.delete(creator);
        assertThat(creatorRepository.findById(savedCreator.getId())).isEmpty();
    }

    @Test
    void tweetRepositoryTest() {
        // Создаем создателя
        Creator creator = Creator.builder()
                .login("tweetuser")
                .password("password123")
                .firstname("Tweet")
                .lastname("User")
                .build();
        Creator savedCreator = creatorRepository.save(creator);

        // Создаем и сохраняем твит
        Tweet tweet = Tweet.builder()
                .creator(savedCreator)
                .title("Test Tweet")
                .content("This is a test tweet content")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        Tweet savedTweet = tweetRepository.save(tweet);

        // Проверяем, что твит сохранился
        assertThat(savedTweet.getId()).isNotNull();

        // Проверяем поиск по ID
        Optional<Tweet> foundTweet = tweetRepository.findById(savedTweet.getId());
        assertThat(foundTweet).isPresent();
        assertThat(foundTweet.get().getTitle()).isEqualTo("Test Tweet");
        assertThat(foundTweet.get().getCreator().getId()).isEqualTo(savedCreator.getId());

        // Проверяем получение всех твитов
        List<Tweet> tweets = tweetRepository.findAll();
        assertThat(tweets).hasSize(1);

        // Проверяем пагинацию и сортировку
        Page<Tweet> tweetPage = tweetRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "created")));
        assertThat(tweetPage.getContent()).hasSize(1);

        // Проверяем обновление
        tweet.setTitle("Updated Tweet");
        Tweet updatedTweet = tweetRepository.save(tweet);
        assertThat(updatedTweet.getTitle()).isEqualTo("Updated Tweet");

        // Проверяем удаление
        tweetRepository.delete(tweet);
        assertThat(tweetRepository.findById(savedTweet.getId())).isEmpty();
    }

    @Test
    void markerRepositoryTest() {
        // Создаем и сохраняем маркер
        Marker marker = Marker.builder()
                .name("TestMarker")
                .build();

        Marker savedMarker = markerRepository.save(marker);

        // Проверяем, что маркер сохранился
        assertThat(savedMarker.getId()).isNotNull();

        // Проверяем поиск по ID
        Optional<Marker> foundMarker = markerRepository.findById(savedMarker.getId());
        assertThat(foundMarker).isPresent();
        assertThat(foundMarker.get().getName()).isEqualTo("TestMarker");

        // Проверяем получение всех маркеров
        List<Marker> markers = markerRepository.findAll();
        assertThat(markers).hasSize(1);

        // Проверяем пагинацию и сортировку
        Page<Marker> markerPage = markerRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
        assertThat(markerPage.getContent()).hasSize(1);

        // Проверяем обновление
        marker.setName("UpdatedMarker");
        Marker updatedMarker = markerRepository.save(marker);
        assertThat(updatedMarker.getName()).isEqualTo("UpdatedMarker");

        // Проверяем удаление
        markerRepository.delete(marker);
        assertThat(markerRepository.findById(savedMarker.getId())).isEmpty();
    }

    @Test
    void noticeRepositoryTest() {
        // Создаем создателя
        Creator creator = Creator.builder()
                .login("noticeuser")
                .password("password123")
                .firstname("Notice")
                .lastname("User")
                .build();
        Creator savedCreator = creatorRepository.save(creator);

        // Создаем твит
        Tweet tweet = Tweet.builder()
                .creator(savedCreator)
                .title("Notice Tweet")
                .content("This is a tweet for notice test")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();
        Tweet savedTweet = tweetRepository.save(tweet);

        // Создаем и сохраняем заметку
        Notice notice = Notice.builder()
                .tweet(savedTweet)
                .content("This is a test notice content")
                .build();

        Notice savedNotice = noticeRepository.save(notice);

        // Проверяем, что заметка сохранилась
        assertThat(savedNotice.getId()).isNotNull();

        // Проверяем поиск по ID
        Optional<Notice> foundNotice = noticeRepository.findById(savedNotice.getId());
        assertThat(foundNotice).isPresent();
        assertThat(foundNotice.get().getContent()).isEqualTo("This is a test notice content");
        assertThat(foundNotice.get().getTweet().getId()).isEqualTo(savedTweet.getId());

        // Проверяем получение всех заметок
        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(1);

        // Проверяем пагинацию и сортировку
        Page<Notice> noticePage = noticeRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")));
        assertThat(noticePage.getContent()).hasSize(1);

        // Проверяем обновление
        notice.setContent("Updated notice content");
        Notice updatedNotice = noticeRepository.save(notice);
        assertThat(updatedNotice.getContent()).isEqualTo("Updated notice content");

        // Проверяем удаление
        noticeRepository.delete(notice);
        assertThat(noticeRepository.findById(savedNotice.getId())).isEmpty();
    }

    @Test
    void tweetMarkerRelationshipTest() {
        // 1. Создаем создателя
        Creator creator = Creator.builder()
                .login("relationuser")
                .password("password123")
                .firstname("Relation")
                .lastname("User")
                .build();
        creatorRepository.save(creator);

        // 2. Создаем маркеры
        Marker marker1 = Marker.builder()
                .name("Marker1")
                .tweets(new HashSet<>()) // Инициализируем пустым множеством
                .build();

        Marker marker2 = Marker.builder()
                .name("Marker2")
                .tweets(new HashSet<>()) // Инициализируем пустым множеством
                .build();

        markerRepository.save(marker1);
        markerRepository.save(marker2);

        // 3. Создаем твит
        Tweet tweet = Tweet.builder()
                .creator(creator)
                .title("Relationship Test")
                .content("Testing relationships between tweets and markers")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .markers(new HashSet<>()) // Инициализируем пустым множеством
                .build();

        // Добавляем маркеры в твит
        tweet.getMarkers().add(marker1);
        tweet.getMarkers().add(marker2);

        // 4. Устанавливаем обратную связь
        marker1.getTweets().add(tweet);
        marker2.getTweets().add(tweet);

        // 5. Сохраняем твит (это должно сохранить и связи в соединительной таблице)
        tweetRepository.save(tweet);

        // 6. Обязательно сохраняем маркеры повторно для обновления обратной связи
        markerRepository.save(marker1);
        markerRepository.save(marker2);

        // 7. Проверяем, что твит имеет маркеры
        Tweet savedTweet = tweetRepository.findById(tweet.getId()).orElseThrow();
        assertThat(savedTweet.getMarkers()).hasSize(2);

        // 8. Проверяем, что маркер связан с твитом
        Marker savedMarker = markerRepository.findById(marker1.getId()).orElseThrow();
        assertThat(savedMarker.getTweets()).hasSize(1);
        assertThat(savedMarker.getTweets().iterator().next().getId()).isEqualTo(tweet.getId());
    }
}