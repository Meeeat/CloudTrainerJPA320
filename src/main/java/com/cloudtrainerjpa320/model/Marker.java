package com.cloudtrainerjpa320.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(schema = "distcomp", name = "tbl_marker")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 32)
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "markers")
    private Set<Tweet> tweets;

}