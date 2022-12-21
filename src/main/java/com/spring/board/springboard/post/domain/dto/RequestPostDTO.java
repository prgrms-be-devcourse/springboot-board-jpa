package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.Member;

import java.time.LocalDateTime;

public class RequestPostDTO {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer memberId;

    public RequestPostDTO() {
    }

    public RequestPostDTO(String title, String content, LocalDateTime createdAt, Integer memberId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.memberId = memberId;
    }

    public RequestPostDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.memberId = post.getMemberId();
    }

    public Post toEntity(RequestPostDTO postDTO, Member member) {
        return new Post(
                postDTO.getTitle(),
                postDTO.getContent(),
                LocalDateTime.now(),
                member
        );
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
