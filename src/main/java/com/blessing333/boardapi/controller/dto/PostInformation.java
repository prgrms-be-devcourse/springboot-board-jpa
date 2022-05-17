package com.blessing333.boardapi.controller.dto;

import com.blessing333.boardapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class PostInformation {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final User writer;
}
