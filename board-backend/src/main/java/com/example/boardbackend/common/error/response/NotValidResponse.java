package com.example.boardbackend.common.error.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotValidResponse {
    private String field;
    private Object value;
    private String message;
}
