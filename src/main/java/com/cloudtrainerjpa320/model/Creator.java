package com.cloudtrainerjpa320.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(schema = "distcomp", name = "tbl_creator")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Length(message = "Must be from 2 to 64 symbols", min = 2, max = 64)
    private String login;

    @Column(nullable = false)
    @Length(message = "Must be between 8 and 128 symbols", min = 8, max = 128)
    private String password;

    @Column(nullable = false)
    @Length(message = "Must be from 2 to 64 symbols", min = 2, max = 64)
    private String firstname;

    @Column(nullable = false)
    @Length(message = "Must be from 2 to 64 symbols", min = 2, max = 64)
    private String lastname;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets;

}