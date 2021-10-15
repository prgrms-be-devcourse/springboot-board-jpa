package com.example.springbootboard.dto;

public class PostDto {
    String title;
    String content;
    Long createdBy;

    public PostDto(){}

    public PostDto(String title, String content, Long createdBy) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getCreatedBy() {
        return createdBy;
    }
}
