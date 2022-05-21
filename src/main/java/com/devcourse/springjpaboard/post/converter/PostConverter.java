package com.devcourse.springjpaboard.post.converter;

import com.devcourse.springjpaboard.model.post.Post;
import com.devcourse.springjpaboard.model.user.User;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UserDto;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    public Post convertPostRequest(CreatePostRequest createPostRequest) {
        Post post = new Post();
        post.setTitle(createPostRequest.title());
        post.setContent(createPostRequest.content());
        post.setCreatedBy(createPostRequest.userDto().name());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(this.convertUserRequest(createPostRequest.userDto()));
        return post;
    }

    private User convertUserRequest(UserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setAge(userDto.age());
        user.setHobby(userDto.hobby());

        return user;
    }

    public PostResponse convertPostResponse(Post post) {
        return new PostResponse(post.getTitle(),
                post.getContent(),
                convertUserResponse(post.getUser()));

    }

    private UserDto convertUserResponse(User user) {
        return new UserDto(user.getName(), user.getHobby(), user.getAge());
    }

}
