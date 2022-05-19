package com.prgrms.springboard.post.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.prgrms.springboard.post.domain.Post;

import lombok.Getter;

@Getter
public class PostsResponse {

    private final long totalRowCount;
    private final int pageSize;
    private final int pageNumber;
    private final boolean sort;
    private final List<PagePostResponse> posts;

    private PostsResponse(long totalRowCount, int pageSize, int pageNumber, boolean sort,
        List<PagePostResponse> posts) {
        this.totalRowCount = totalRowCount;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.sort = sort;
        this.posts = posts;
    }

    public static PostsResponse of(Page<PagePostResponse> posts) {
        return new PostsResponse(
            posts.getTotalElements(),
            posts.getSize(),
            posts.getNumber(),
            posts.getSort().isSorted(),
            posts.getContent()
        );
    }

    @Getter
    public static class PagePostResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String writer;

        private PagePostResponse(Long id, String title, String content, String writer) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.writer = writer;
        }

        public static PagePostResponse from(Post post) {
            return new PagePostResponse(
                post.getId(),
                post.getTitle().getTitle(),
                post.getContent().getContent(),
                post.getUser().getName().getValue()
            );
        }
    }

}
