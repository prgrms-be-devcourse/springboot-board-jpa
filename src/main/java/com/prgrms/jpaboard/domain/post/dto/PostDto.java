package com.prgrms.jpaboard.domain.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private Long id;
    private String title;
    private UserInfoDto user;
    private LocalDateTime createdAt;

    public PostDto(Long id, String title, UserInfoDto user, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.createdAt = createdAt;
    }

    public static PostResponseDtoBuilder builder() {
        return new PostResponseDtoBuilder();
    }

    public static class PostResponseDtoBuilder {
        private Long id;
        private String title;
        private UserInfoDto user;
        private LocalDateTime createdAt;

        public PostResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostResponseDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostResponseDtoBuilder user(UserInfoDto user) {
            this.user = user;
            return this;
        }

        public PostResponseDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostDto build() {
            return new PostDto(id, title, user, createdAt);
        }
    }
}
