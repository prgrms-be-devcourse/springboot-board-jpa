package me.kimihiqq.springbootboardjpa.global.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String debugMessage;
}

