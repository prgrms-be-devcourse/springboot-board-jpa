package org.prgms.springbootboardjpayu.service.converter;

import org.prgms.springbootboardjpayu.domain.Post;
import org.prgms.springbootboardjpayu.domain.User;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.ListResponse;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.springframework.data.domain.Page;

public final class PostConverter {
    private PostConverter() {
    }

    public static Post toPost(CreatePostRequest request, User user) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
    }

    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCratedAt())
                .user(UserConverter.toUserProfile(post.getUser()))
                .build();
    }

    public static ListResponse toPostListResponse(Page<PostResponse> page) {
        return ListResponse.builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getNumberOfElements())
                .build();
    }
}
