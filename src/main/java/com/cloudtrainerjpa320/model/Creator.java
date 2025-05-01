package com.cloudtrainerjpa320.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_creator")
@Getter
@Setter
public class Creator extends BaseEntity {

    @Column(name = "login", nullable = false, length = 64, unique = true)
    private String login;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "firstname", nullable = false, length = 64)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 64)
    private String lastname;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();
}