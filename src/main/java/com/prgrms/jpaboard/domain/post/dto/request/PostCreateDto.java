package com.prgrms.jpaboard.domain.post.dto.request;

import com.prgrms.jpaboard.domain.post.domain.Post;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class PostCreateDto {
    @NotNull
    private Long userId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    private String content;

    public PostCreateDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        LocalDateTime now = LocalDateTime.now();

        return Post.builder()
                .title(title)
                .content(content)
                .createdBy(userId.toString())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
