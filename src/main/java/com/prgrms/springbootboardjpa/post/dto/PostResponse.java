package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.domain.Post;
import com.prgrms.springbootboardjpa.user.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class PostResponse {
    private long postId;
    private long createdBy;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostResponse() {
    }

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.createdBy = post.getCreatedBy().getMemberId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return getPostId() == that.getPostId() && getCreatedBy() == that.getCreatedBy() && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContent(), that.getContent()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getCreatedBy(), getTitle(), getContent(), getCreatedAt());
    }
}
