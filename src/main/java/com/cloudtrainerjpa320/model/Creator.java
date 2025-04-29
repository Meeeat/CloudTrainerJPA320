package com.cloudtrainerjpa320.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creator {

    Long id;
    String login;
    String password;
    String firstname;
    String lastname;

}