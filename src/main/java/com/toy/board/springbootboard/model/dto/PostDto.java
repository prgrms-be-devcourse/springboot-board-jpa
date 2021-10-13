package com.toy.board.springbootboard.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostDto {
    private long id;
    private String title;
    private String content;
    private UserDto userDto;
    private LocalDateTime createdAt;
    private String createdBy;
}
