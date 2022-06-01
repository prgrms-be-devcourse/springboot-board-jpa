package com.hyunji.jpaboard.web.dto;

import com.hyunji.jpaboard.domain.post.domain.Post;
import com.hyunji.jpaboard.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class PostDto {

    private PostDto() {
    }

    @Getter
    @Setter
    public static class Request {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private String username;

        public Post toEntity(User user) {
            return new Post(this.title, this.content, user);
        }
    }

    @Getter
    @Setter
    public static class PostUpdateRequest {
        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

    @Getter
    public static class Response {
        private final String title;
        private final String content;
        private final String userName;

        public Response(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.userName = post.getUser().getName();
        }
    }

}
