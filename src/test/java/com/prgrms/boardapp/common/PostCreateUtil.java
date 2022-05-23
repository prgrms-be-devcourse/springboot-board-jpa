package com.prgrms.boardapp.common;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.model.Post;

import java.time.LocalDateTime;

import static com.prgrms.boardapp.common.UserCreateUtil.createUserDto;

public class PostCreateUtil {

    private static Long postId = 1L;

    private PostCreateUtil() {
    }

    private synchronized static void increaseId() {
        postId++;
    }

    public static Post createPost() {
        return Post.builder()
                .title("sample title")
                .content("sample content")
                .build();
    }

    public synchronized static Post createPostWithId() {
        return Post.builder()
                .id(postId++)
                .title("sample title")
                .content("sample content")
                .build();
    }

    public static Post createPostWithTitle(String title) {
        return Post.builder()
                .title(title)
                .build();
    }

    public synchronized static PostDto createPostDto() {
        return PostDto.builder()
                .id(postId++)
                .createdAt(LocalDateTime.now())
                .title("title")
                .content("content")
                .userDto(createUserDto())
                .build();
    }
}
