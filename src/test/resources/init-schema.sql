-- Создание схемы distcomp, если она еще не существует
CREATE SCHEMA IF NOT EXISTS distcomp;

-- Создание таблицы tbl_creator
CREATE TABLE IF NOT EXISTS distcomp.tbl_creator (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(64) NOT NULL,
    password VARCHAR(128) NOT NULL,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL
);

-- Создание таблицы tbl_tweet
CREATE TABLE IF NOT EXISTS distcomp.tbl_tweet (
    id BIGSERIAL PRIMARY KEY,
    creator_id BIGINT NOT NULL,
    title VARCHAR(64) NOT NULL,
    content VARCHAR(2048) NOT NULL,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL,
    CONSTRAINT fk_tweet_creator FOREIGN KEY (creator_id) REFERENCES distcomp.tbl_creator(id)
);

-- Создание таблицы tbl_marker
CREATE TABLE IF NOT EXISTS distcomp.tbl_marker (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

-- Создание таблицы tbl_notice
CREATE TABLE IF NOT EXISTS distcomp.tbl_notice (
    id BIGSERIAL PRIMARY KEY,
    tweet_id BIGINT NOT NULL,
    content VARCHAR(2048) NOT NULL,
    CONSTRAINT fk_notice_tweet FOREIGN KEY (tweet_id) REFERENCES distcomp.tbl_tweet(id)
);

-- Создание таблицы связи между твитами и маркерами
CREATE TABLE IF NOT EXISTS distcomp.tbl_tweet_marker (
    tweet_id BIGINT NOT NULL,
    marker_id BIGINT NOT NULL,
    PRIMARY KEY (tweet_id, marker_id),
    CONSTRAINT fk_tweetmarker_tweet FOREIGN KEY (tweet_id) REFERENCES distcomp.tbl_tweet(id),
    CONSTRAINT fk_tweetmarker_marker FOREIGN KEY (marker_id) REFERENCES distcomp.tbl_marker(id)
);