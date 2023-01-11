package com.example.board.global.dto;

import java.time.LocalDateTime;

import com.example.board.util.MessageUtil;

public record InnerErrorResponse(String status,
                                 String message,
                                 LocalDateTime responsedAt) {

    public static InnerErrorResponse of(String status, String message) {
        return new InnerErrorResponse(status, MessageUtil.getMessage(message), LocalDateTime.now());
    }
}
