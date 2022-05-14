package com.prgrms.jpaboard.domain.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDetailDto extends PostDto{
    private String content;

    public PostDetailDto(Long id, String title, UserInfoDto user, LocalDateTime createdAt, String content) {
        super(id, title, user, createdAt);
        this.content = content;
    }

    public static PostDetailDtoBuilder postDetailDtoBuilder() {
        return new PostDetailDtoBuilder();
    }

    public static class PostDetailDtoBuilder {
        private Long id;
        private String title;
        private UserInfoDto user;
        private LocalDateTime createdAt;
        private String content;

        public PostDetailDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostDetailDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostDetailDtoBuilder user(UserInfoDto user) {
            this.user = user;
            return this;
        }

        public PostDetailDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostDetailDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostDetailDto build() {
            return new PostDetailDto(id, title, user, createdAt, content);
        }
    }
}
