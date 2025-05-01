package com.cloudtrainerjpa320.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_tweet")
@Getter
@Setter
public class Tweet extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @Column(name = "title", nullable = false, length = 64, unique = true)
    private String title;

    @Column(name = "content", nullable = false, length = 2048)
    private String content;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Notice> notices = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_tweet_marker",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "marker_id")
    )
    private Set<Marker> markers = new HashSet<>();
}