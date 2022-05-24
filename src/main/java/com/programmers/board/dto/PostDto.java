package com.programmers.board.dto;

import com.programmers.board.entity.Post;

public class PostDto {
    private final String title;
    private final String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post postConvertor() {
        if (this.title == null) {
            throw new IllegalArgumentException("제목이 없습니다.");
        }
        if (this.content == null) {
            throw new IllegalArgumentException("내용이 없습니다.");
        }
        return new Post(this.title, this.content);
    }
}
