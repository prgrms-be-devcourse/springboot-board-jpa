package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.MemberResponseDTO;

import java.time.LocalDateTime;

public class ResponsePostDTO {
    private Integer postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private MemberResponseDTO member;

    public ResponsePostDTO() {
    }

    public ResponsePostDTO(Post post, MemberResponseDTO memberResponseDTO){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.member = memberResponseDTO;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public MemberResponseDTO getMember() {
        return member;
    }
}
