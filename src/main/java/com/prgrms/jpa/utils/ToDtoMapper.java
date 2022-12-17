package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.post.PostIdResponse;
import com.prgrms.jpa.controller.dto.post.PostResponse;
import com.prgrms.jpa.controller.dto.post.PostsResponse;
import com.prgrms.jpa.controller.dto.user.UserIdResponse;
import com.prgrms.jpa.domain.Post;

import java.util.List;
import java.util.stream.Collectors;

public class ToDtoMapper {

    private ToDtoMapper() {
    }

    public static UserIdResponse toUserIdDto(long id) {
        return new UserIdResponse(id);
    }

    public static PostIdResponse toPostIdDto(long id) {
        return new PostIdResponse(id);
    }

    public static PostResponse toPostDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();
    }

    public static PostsResponse toPostsDto(List<Post> posts, long totalPages, long totalCount) {
        return PostsResponse.builder()
                .totalPages(totalPages)
                .totalCount(totalCount)
                .posts(posts.stream()
                        .map(ToDtoMapper::toPostDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
