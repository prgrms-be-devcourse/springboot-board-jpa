package com.toy.board.springbootboard.model.dto;

import com.toy.board.springbootboard.model.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostDto {
    private long id;
    private String title;
    private String content;
    private long userId;
}
