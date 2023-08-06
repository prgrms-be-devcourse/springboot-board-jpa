package com.example.jpaboard.global;

import java.time.LocalDateTime;

public record ErrorResponse(int statusCode, String detail, String instance, String time) {

    public ErrorResponse(int statusCode, String detail, String instance) {
        this(statusCode, detail, instance, LocalDateTime.now().toString());
    }

}
