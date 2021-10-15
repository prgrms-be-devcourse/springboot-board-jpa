package com.example.springbootboard.dto;

public class PostDto {
    String title;
    String content;
    Long created_by;

    public PostDto(){}

    public PostDto(String title, String content, Long created_by) {
        this.title = title;
        this.content = content;
        this.created_by = created_by;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getCreated_by() {
        return created_by;
    }
}
