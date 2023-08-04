package dev.jpaboard.post.dto;

import dev.jpaboard.post.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostsResponse(List<PostResponse> postResponse, int totalPages, int currentPage) {

    public static PostsResponse from(Page<Post> postPage) {
        return new PostsResponse(postPage.getContent().stream()
                .map(PostResponse::from)
                .toList(), postPage.getTotalPages(), postPage.getNumber());
    }

}
