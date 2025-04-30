package com.cloudtrainerjpa320.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "distcomp", name = "tbl_marker")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"tweets"})
@EqualsAndHashCode(exclude = {"tweets"})
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 32)
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "markers")
    @Builder.Default
    private Set<Tweet> tweets = new HashSet<>();

}