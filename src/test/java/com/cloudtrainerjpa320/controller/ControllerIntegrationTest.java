package com.cloudtrainerjpa320.controller;

import com.cloudtrainerjpa320.RestApplication;
import com.cloudtrainerjpa320.mapper.creator.CreatorRequestTo;
import com.cloudtrainerjpa320.mapper.marker.MarkerRequestTo;
import com.cloudtrainerjpa320.mapper.notice.NoticeRequestTo;
import com.cloudtrainerjpa320.mapper.tweet.TweetRequestTo;
import com.cloudtrainerjpa320.model.Creator;
import com.cloudtrainerjpa320.model.Tweet;
import com.cloudtrainerjpa320.repository.CreatorRepository;
import com.cloudtrainerjpa320.repository.MarkerRepository;
import com.cloudtrainerjpa320.repository.NoticeRepository;
import com.cloudtrainerjpa320.repository.TweetRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = RestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ControllerIntegrationTest {

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
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update"); // Используем update вместо create-drop
        registry.add("spring.liquibase.enabled", () -> "false"); // Отключаем Liquibase, т.к. таблицы уже созданы init-script
    }

    @LocalServerPort
    private int port;

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private MarkerRepository markerRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1.0";

        // Очистка базы данных перед каждым тестом
        noticeRepository.deleteAll();
        tweetRepository.deleteAll();
        markerRepository.deleteAll();
        creatorRepository.deleteAll();
    }

    @Test
    void creatorCrudOperationsTest() {
        // Создание Creator
        CreatorRequestTo createRequest = CreatorRequestTo.builder()
                .login("testuser")
                .password("password123")
                .firstname("Test")
                .lastname("User")
                .build();

        // POST запрос для создания Creator
        Integer creatorId = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/creators")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("login", equalTo("testuser"))
                .body("firstname", equalTo("Test"))
                .body("lastname", equalTo("User"))
                .extract()
                .path("id");

        // GET запрос для получения созданного Creator
        given()
                .when()
                .get("/creators/{id}", creatorId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(creatorId.intValue()))
                .body("login", equalTo("testuser"))
                .body("firstname", equalTo("Test"))
                .body("lastname", equalTo("User"));

        // GET запрос для получения всех Creator
        given()
                .when()
                .get("/creators")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(creatorId.intValue()));

        // GET запрос с пагинацией
        given()
                .when()
                .get("/creators/paged?page=0&size=10")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content.size()", equalTo(1))
                .body("content[0].id", equalTo(creatorId.intValue()));

        // PUT запрос для обновления Creator
        CreatorRequestTo updateRequest = CreatorRequestTo.builder()
                .id(creatorId.longValue()) // Преобразуем Integer в Long
                .login("updateduser")
                .password("updatedpass")
                .firstname("Updated")
                .lastname("User")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/creators")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(creatorId.intValue()))
                .body("login", equalTo("updateduser"))
                .body("firstname", equalTo("Updated"));

        // DELETE запрос для удаления Creator
        given()
                .when()
                .delete("/creators/{id}", creatorId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Проверка что Creator удален
        given()
                .when()
                .get("/creators/{id}", creatorId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void markerCrudOperationsTest() {
        // Создание Marker
        MarkerRequestTo createRequest = MarkerRequestTo.builder()
                .name("TestMarker")
                .build();

        // POST запрос для создания Marker
        Integer markerId = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/markers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("TestMarker"))
                .extract()
                .path("id");

        // GET запрос для получения созданного Marker
        given()
                .when()
                .get("/markers/{id}", markerId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(markerId.intValue()))
                .body("name", equalTo("TestMarker"));

        // GET запрос для получения всех Marker
        given()
                .when()
                .get("/markers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(markerId.intValue()));

        // PUT запрос для обновления Marker
        MarkerRequestTo updateRequest = MarkerRequestTo.builder()
                .id(markerId.longValue()) // Преобразуем Integer в Long
                .name("UpdatedMarker")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/markers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(markerId.intValue()))
                .body("name", equalTo("UpdatedMarker"));

        // DELETE запрос для удаления Marker
        given()
                .when()
                .delete("/markers/{id}", markerId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Проверка что Marker удален
        given()
                .when()
                .get("/markers/{id}", markerId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void tweetCrudOperationsTest() {
        // Сначала создаем Creator для Tweet
        Creator creator = Creator.builder()
                .login("tweetuser")
                .password("password123")
                .firstname("Tweet")
                .lastname("User")
                .build();

        Creator savedCreator = creatorRepository.save(creator);

        // Создание Tweet
        TweetRequestTo createRequest = TweetRequestTo.builder()
                .creatorId(savedCreator.getId())
                .title("Test Tweet")
                .content("This is a test tweet content")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        // POST запрос для создания Tweet
        Integer tweetId = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/tweets")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("title", equalTo("Test Tweet"))
                .body("content", equalTo("This is a test tweet content"))
                .body("creatorId", equalTo(savedCreator.getId().intValue()))
                .extract()
                .path("id");

        // GET запрос для получения созданного Tweet
        given()
                .when()
                .get("/tweets/{id}", tweetId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(tweetId.intValue()))
                .body("title", equalTo("Test Tweet"))
                .body("creatorId", equalTo(savedCreator.getId().intValue()));

        // GET запрос для получения всех Tweet
        given()
                .when()
                .get("/tweets")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(tweetId.intValue()));

        // PUT запрос для обновления Tweet
        TweetRequestTo updateRequest = TweetRequestTo.builder()
                .id(tweetId.longValue()) // Преобразуем Integer в Long
                .creatorId(savedCreator.getId())
                .title("Updated Tweet")
                .content("This is an updated tweet content")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/tweets")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(tweetId.intValue()))
                .body("title", equalTo("Updated Tweet"))
                .body("content", equalTo("This is an updated tweet content"));

        // DELETE запрос для удаления Tweet
        given()
                .when()
                .delete("/tweets/{id}", tweetId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Проверка что Tweet удален
        given()
                .when()
                .get("/tweets/{id}", tweetId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void noticeCrudOperationsTest() {
        // Сначала создаем Creator и Tweet для Notice
        Creator creator = Creator.builder()
                .login("noticeuser")
                .password("password123")
                .firstname("Notice")
                .lastname("User")
                .build();

        Creator savedCreator = creatorRepository.save(creator);

        Tweet tweet = Tweet.builder()
                .creator(savedCreator)
                .title("Notice Tweet")
                .content("This is a tweet for notice test")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .notices(new ArrayList<>())
                .markers(new HashSet<>())
                .build();

        Tweet savedTweet = tweetRepository.save(tweet);

        // Создание Notice
        NoticeRequestTo createRequest = NoticeRequestTo.builder()
                .tweetId(savedTweet.getId())
                .content("This is a test notice content")
                .build();

        // POST запрос для создания Notice
        Integer noticeId = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/notices")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("content", equalTo("This is a test notice content"))
                .body("tweetId", equalTo(savedTweet.getId().intValue()))
                .extract()
                .path("id");

        // GET запрос для получения созданного Notice
        given()
                .when()
                .get("/notices/{id}", noticeId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(noticeId.intValue()))
                .body("content", equalTo("This is a test notice content"))
                .body("tweetId", equalTo(savedTweet.getId().intValue()));

        // GET запрос для получения всех Notice
        given()
                .when()
                .get("/notices")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(noticeId.intValue()));

        // PUT запрос для обновления Notice
        NoticeRequestTo updateRequest = NoticeRequestTo.builder()
                .id(noticeId.longValue()) // Преобразуем Integer в Long
                .tweetId(savedTweet.getId())
                .content("This is an updated notice content")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/notices")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(noticeId.intValue()))
                .body("content", equalTo("This is an updated notice content"));

        // DELETE запрос для удаления Notice
        given()
                .when()
                .delete("/notices/{id}", noticeId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Проверка что Notice удален
        given()
                .when()
                .get("/notices/{id}", noticeId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void validationErrorTest() {
        // Создание Creator с невалидными данными
        CreatorRequestTo invalidRequest = CreatorRequestTo.builder()
                .login("u") // Слишком короткий логин (минимум 2 символа)
                .password("pass") // Слишком короткий пароль (минимум 8 символов)
                .firstname("T")  // Слишком короткое имя (минимум 2 символа)
                .lastname("U")   // Слишком короткая фамилия (минимум 2 символа)
                .build();

        // POST запрос должен вернуть ошибку валидации
        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post("/creators")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo(40001))
                .body("errorMessage", containsString("Validation failed"));
    }

    @Test
    void notFoundErrorTest() {
        // GET запрос с несуществующим ID
        given()
                .when()
                .get("/creators/999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        // PUT запрос с несуществующим ID
        CreatorRequestTo updateRequest = CreatorRequestTo.builder()
                .id(999L)
                .login("notfounduser")
                .password("password123")
                .firstname("NotFound")
                .lastname("User")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/creators")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        // DELETE запрос с несуществующим ID
        given()
                .when()
                .delete("/creators/999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}