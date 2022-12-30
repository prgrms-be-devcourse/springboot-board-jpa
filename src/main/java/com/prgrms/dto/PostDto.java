package com.prgrms.dto;

import com.prgrms.domain.post.Post;
import com.prgrms.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PostDto {

    public record Request(@NotBlank String title, @NotBlank String content, @NotNull Long userId) {

        public Post toPost(User user) {
            return new Post(title, content, user);
        }
    }

    public record Response(Post post) {

        public long getPostId() {
            return post.getId();
        }
    }

    public record Update(@NotBlank String title, @NotBlank String content) {

    }

    public record ResponsePostDtos(List<Response> postDtos) {

    }

}
