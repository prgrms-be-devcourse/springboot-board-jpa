package com.devcourse.springjpaboard.post.converter;

import com.devcourse.springjpaboard.model.post.Post;
import com.devcourse.springjpaboard.model.user.User;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    public Post convertPostRequest(CreatePostRequest createPostRequest, User user) {
        Post post = new Post();
        post.setTitle(createPostRequest.title());
        post.setContent(createPostRequest.content());
        post.setCreatedBy(user.getName());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        return post;
    }

    public PostResponse convertPostResponse(Post post) {
        return new PostResponse(post.getTitle(), post.getContent());
    }

}
