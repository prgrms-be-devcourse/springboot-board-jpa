package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponsePost {
    
    private Long id;
    
    private String title;
    
    private String content;
   
    private LocalDateTime createdAt;

    @Builder
    public ResponsePost(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
    
    public static ResponsePost of(Post post){
        return ResponsePost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
