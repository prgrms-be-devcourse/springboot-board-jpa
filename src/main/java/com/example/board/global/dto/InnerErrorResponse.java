package com.example.board.global.dto;

import com.example.board.util.MessageUtil;

import java.time.LocalDateTime;

public record InnerErrorResponse(String status,
                                 String message,
                                 LocalDateTime responsedAt) {

    public static InnerErrorResponse of(String status, String message) {
        return new InnerErrorResponse(status, MessageUtil.getMessage(message), LocalDateTime.now());
    }
}
