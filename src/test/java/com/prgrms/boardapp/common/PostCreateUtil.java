package com.prgrms.boardapp.common;

import com.prgrms.boardapp.dto.PostRequest;
import com.prgrms.boardapp.dto.PostResponse;
import com.prgrms.boardapp.dto.UserResponse;
import com.prgrms.boardapp.model.Post;

import static com.prgrms.boardapp.common.UserCreateUtil.createUserResponse;

public class PostCreateUtil {

    private static Long postId = 1L;

    private PostCreateUtil() {
    }

    public static Post createPost() {
        return Post.builder()
                .title("sample title")
                .content("sample content")
                .build();
    }

    public static Post createPostWithId() {
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

    public static PostRequest createPostRequest() {
        return PostRequest.builder()
                .title("title")
                .content("content")
                .build();
    }

    public static PostResponse createPostResponseWithId(Long id) {
        UserResponse userResponse = createUserResponse();
        return PostResponse.builder()
                .id(id)
                .user(userResponse)
                .content("post response content")
                .title("post response title")
                .build();
    }
}
