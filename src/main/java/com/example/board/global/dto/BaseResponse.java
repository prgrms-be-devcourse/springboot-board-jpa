package com.example.board.global.dto;

import com.example.board.util.MessageUtil;
import org.springframework.http.HttpStatus;

public record BaseResponse<T>(HttpStatus status,
                              String title,
                              String content,
                              T stock) {

    public static <T> BaseResponse<T> of(HttpStatus status, String title, String content, T stock) {
        return new BaseResponse<>(status,
                MessageUtil.getMessage(title),
                MessageUtil.getMessage(content),
                stock);
    }
}
