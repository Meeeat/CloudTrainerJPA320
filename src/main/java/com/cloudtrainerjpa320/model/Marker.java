package com.cloudtrainerjpa320.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_marker")
@Getter
@Setter
public class Marker extends BaseEntity {

    @Column(name = "name", nullable = false, length = 32, unique = true)
    private String name;

    @ManyToMany(mappedBy = "markers")
    private Set<Tweet> tweets = new HashSet<>();
}