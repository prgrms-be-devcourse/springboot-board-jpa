package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ResponsePost {
    
    private Long id;
    
    private String title;
    
    private String content;

    @Builder
    public ResponsePost(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
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
