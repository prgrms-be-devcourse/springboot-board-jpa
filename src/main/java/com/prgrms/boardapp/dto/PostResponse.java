package com.prgrms.boardapp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private UserResponse user;

    private PostResponse(Long id, String title, String content, UserResponse user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static PostResponseBuilder builder() {
        return new PostResponseBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UserResponse getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(user.getId(), that.user.getId());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public static class PostResponseBuilder {
        private Long id;
        private String title;
        private String content;
        private UserResponse user;

        PostResponseBuilder() {
        }

        public PostResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PostResponseBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public PostResponseBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public PostResponseBuilder user(final UserResponse user) {
            this.user = user;
            return this;
        }

        public PostResponse build() {
            return new PostResponse(this.id, this.title, this.content, this.user);
        }
    }
}
