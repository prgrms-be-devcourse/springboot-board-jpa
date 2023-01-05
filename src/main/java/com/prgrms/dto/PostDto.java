package com.prgrms.dto;

import com.prgrms.domain.post.Post;
import com.prgrms.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class PostDto {

    public record Request(@NotBlank String title, @NotBlank String content) {

        public Post toPost(User user) {
            return new Post(title, content, user);
        }
    }

    public record Response(Long id, String title, String content, Long userId, String userName) {

        public static Response from(Post post) {
            return new Response(post.getId(), post.getTitle(), post.getContent(),
                post.getUser().getId(), post.getUser().getName());
        }

        public long getPostId() {
            return id;
        }
    }

    public record Update(@NotBlank String title, @NotBlank String content) {

    }

    public record ResponsePostDtos(List<Response> postDtos) {

    }

}
