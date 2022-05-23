package com.devcourse.springjpaboard.post.controller.stub;

import com.devcourse.springjpaboard.model.post.Post;
import com.devcourse.springjpaboard.model.user.User;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class PostStubs {

    private PostStubs() {
    }

    public static CreatePostRequest createPostRequest() {
        return new CreatePostRequest("test-title", "test-content", 1L);
    }

    public static UpdatePostRequest updatePostRequest() {
        return new UpdatePostRequest("update-title", "update-content");
    }

    public static PostResponse createPostResponse() {
        return new PostResponse("test-title", "test-content");
    }

    public static PostResponse updatePostResponse() {
        return new PostResponse("update-title", "update-content");
    }

    public static Page<PostResponse> pagePostResponse() {
        return new PageImpl<>(
                List.of(
                        new PostResponse("test-title1", "test-content1"),
                        new PostResponse("test-title2", "test-content2"),
                        new PostResponse("test-title3", "test-content3"),
                        new PostResponse("test-title4", "test-content4"),
                        new PostResponse("test-title5", "test-content5")
                )
        );
    }

    public static Post post(User user) {
        Post post = new Post();
        post.setId(1L);
        post.setUser(user);
        post.setTitle("test-title");
        post.setContent("test-content");
        post.setCreatedAt(LocalDateTime.now());
        post.setCreatedBy(user.getName());
        return post;
    }
}
