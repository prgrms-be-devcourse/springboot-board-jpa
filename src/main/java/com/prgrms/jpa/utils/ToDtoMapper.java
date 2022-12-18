package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.post.CreatePostResponse;
import com.prgrms.jpa.controller.dto.post.GetByIdPostResponse;
import com.prgrms.jpa.controller.dto.post.FindAllPostResponse;
import com.prgrms.jpa.controller.dto.user.CreateUserResponse;
import com.prgrms.jpa.domain.Post;

import java.util.List;
import java.util.stream.Collectors;

public class ToDtoMapper {

    private ToDtoMapper() {
    }

    public static CreateUserResponse toUserIdDto(long id) {
        return new CreateUserResponse(id);
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
                        .map(ToDtoMapper::toPostDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
