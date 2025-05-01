package com.cloudtrainerjpa320.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestTo {

    @NotNull(message = "ID темы обязателен")
    @JsonProperty("tweetId")
    private Long tweetId;

    @NotBlank(message = "Содержание уведомления обязательно")
    @Size(min = 2, max = 2048, message = "Содержание должно содержать от 2 до 2048 символов")
    @JsonProperty("content")
    private String content;
}