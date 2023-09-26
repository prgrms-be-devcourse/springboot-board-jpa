package devcource.hihi.boardjpa.dto.post;

import devcource.hihi.boardjpa.domain.Post;
import jakarta.validation.constraints.NotNull;

public record CreateRequestDto(@NotNull String title, String content) {

    public Post toEntity() {
        return new Post(title, content);
    }


    public static CreateRequestDto toCreateDto(Post post) {
        return new CreateRequestDto(post.getTitle(), post.getContent());
    }

}

