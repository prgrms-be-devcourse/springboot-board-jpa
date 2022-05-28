package com.prgrms.jpaboard.domain.post.dto.request;

import com.prgrms.jpaboard.domain.post.domain.Post;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
public class PostCreateDto {
    @NotNull
    private Long userId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    private String content;

    public PostCreateDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .createdBy(userId.toString())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostCreateDto that = (PostCreateDto) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTitle(), getContent());
    }
}
