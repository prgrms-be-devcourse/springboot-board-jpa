package com.example.springbootboard.dto;

import com.example.springbootboard.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;

    @Builder
    public PostDto(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
