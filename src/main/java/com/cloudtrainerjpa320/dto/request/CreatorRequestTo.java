package com.cloudtrainerjpa320.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorRequestTo {

    @NotBlank(message = "Логин обязателен")
    @Size(min = 2, max = 64, message = "Логин должен содержать от 2 до 64 символов")
    @JsonProperty("login")
    private String login;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, max = 128, message = "Пароль должен содержать от 8 до 128 символов")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 64, message = "Имя должно содержать от 2 до 64 символов")
    @JsonProperty("firstname")
    private String firstname;

    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 64, message = "Фамилия должна содержать от 2 до 64 символов")
    @JsonProperty("lastname")
    private String lastname;
}