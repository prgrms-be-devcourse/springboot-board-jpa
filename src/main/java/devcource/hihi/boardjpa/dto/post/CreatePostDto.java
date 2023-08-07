package devcource.hihi.boardjpa.dto.post;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public record CreatePostDto(@NotNull String title, String content, @NotNull User user) {

    public Post toEntity() {
        return new Post(title, content,user);
    }

}

