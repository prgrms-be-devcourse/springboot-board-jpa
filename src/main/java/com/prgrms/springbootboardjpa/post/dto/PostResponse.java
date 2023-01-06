package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PostResponse {
    private long postId;
    private long createdBy;
    private String title;
    private String content;

    public PostResponse() {
    }

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.createdBy = post.getCreatedBy().getMemberId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return getPostId() == that.getPostId() && getCreatedBy() == that.getCreatedBy() && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getCreatedBy(), getTitle(), getContent());
    }
}
