package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.CreatePostResponse;
import com.prgrms.jpa.controller.dto.post.FindAllPostResponse;
import com.prgrms.jpa.controller.dto.post.GetByIdPostResponse;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class PostEntityDtoMapper {

    private PostEntityDtoMapper() {
    }

    public static CreatePostResponse toPostIdDto(long id) {
        return new CreatePostResponse(id);
    }

    public static GetByIdPostResponse toPostDto(Post post) {
        return GetByIdPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();
    }

    public static FindAllPostResponse toPostsDto(List<Post> posts, long totalPages, long totalCount) {
        return FindAllPostResponse.builder()
                .totalPages(totalPages)
                .totalCount(totalCount)
                .posts(posts.stream()
                        .map(PostEntityDtoMapper::toPostDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Post toPost(CreatePostRequest createPostRequest, User user) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .user(user)
                .build();
    }
}
