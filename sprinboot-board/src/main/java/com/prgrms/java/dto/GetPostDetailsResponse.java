package com.prgrms.java.dto;

import com.prgrms.java.domain.Post;

import java.util.Objects;

public class GetPostDetailsResponse {

    private final long id;
    private final String title;
    private final String content;

    private GetPostDetailsResponse(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static GetPostDetailsResponse from(Post post) {
        return new GetPostDetailsResponse(post.getId(), post.getTitle(), post.getContent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPostDetailsResponse that = (GetPostDetailsResponse) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }
}
