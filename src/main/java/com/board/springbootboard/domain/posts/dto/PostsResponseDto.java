package com.board.springbootboard.domain.posts.dto;

import com.board.springbootboard.domain.posts.PostsEntity;
import lombok.Getter;

// Entity 필드 중 일부만 사용
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(PostsEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
