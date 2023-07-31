package devcource.hihi.boardjpa.dto.post;

import devcource.hihi.boardjpa.domain.Post;
import jakarta.validation.constraints.NotNull;

public record CreatePostDto(@NotNull String title, String content, @NotNull Long userId) {

    public Post toEntity() {
        return new Post(title, content);
    }

}

