package com.cloudtrainerjpa320.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String errorCode;
    private String errorMessage;
    private String path;

    public static ErrorResponse of(String errorCode, String errorMessage, String path) {
        return new ErrorResponse(LocalDateTime.now(), errorCode, errorMessage, path);
    }
}