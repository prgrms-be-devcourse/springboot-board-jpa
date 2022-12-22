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

    public record Response(@NotNull Long id, @NotBlank String title, @NotBlank String content,
                           @NotNull Long userId, @NotBlank String userName) {

    }

    public record update(@NotBlank String title, @NotBlank String content) {

    }

    public record ResponseArray(List<Response> postDtos) {

    }

}
