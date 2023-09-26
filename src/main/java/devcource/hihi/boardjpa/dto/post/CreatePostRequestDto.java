package devcource.hihi.boardjpa.dto.post;

import devcource.hihi.boardjpa.domain.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreatePostRequestDto(@NotNull String title, String content, Long userId) {

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}

