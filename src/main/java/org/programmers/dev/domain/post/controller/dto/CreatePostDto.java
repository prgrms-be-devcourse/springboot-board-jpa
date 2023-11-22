package org.programmers.dev.domain.post.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.programmers.dev.domain.post.domain.entity.Post;

@NoArgsConstructor
@Data
public class CreatePostDto {

    private String title;
    private String content;

    @Builder
    private CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post of() {
        return Post.builder()
            .title(this.title)
            .content(this.content)
            .build();
    }
}
