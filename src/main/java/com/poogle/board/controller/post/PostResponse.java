package com.poogle.board.controller.post;

import com.poogle.board.controller.user.UserResponse;
import com.poogle.board.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;
    private UserResponse userResponse;

    public PostResponse(Post source) {
        copyProperties(source, this);
    }
}
