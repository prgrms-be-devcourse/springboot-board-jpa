package com.prgrms.boardapp.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostDto {
    private Long id;
    private UserDto userDto;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private PostDto(Long id, UserDto userDto, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userDto = userDto;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto postDto = (PostDto) o;
        return Objects.equals(id, postDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public static PostDtoBuilder builder() {
        return new PostDtoBuilder();
    }

    public static class PostDtoBuilder {
        private Long id;
        private UserDto userDto;
        private String title;
        private String content;
        private LocalDateTime createdAt;

        PostDtoBuilder() {
        }

        public PostDtoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PostDtoBuilder userDto(final UserDto userDto) {
            this.userDto = userDto;
            return this;
        }

        public PostDtoBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public PostDtoBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public PostDtoBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostDto build() {
            return new PostDto(this.id, this.userDto, this.title, this.content, this.createdAt);
        }
    }
}
