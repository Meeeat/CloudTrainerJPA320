package com.cloudtrainerjpa320.mapper.creator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorResponseTo {

    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
}