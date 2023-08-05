package com.programmers.jpa_board.global.converter;

import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter {
    public User createUsertoUser(CreateUserRequest dto) {
        return new User(dto.name(), dto.age(), dto.hobby());
    }

    public UserResponse userToDto(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getPosts(), user.getCreatedAt());
    }

    public Post createPostToPost(CreatePostRequest dto) {
        return new Post(dto.title(), dto.content());
    }

    public PostResponse postToDto(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getUser().getName(), post.getCreatedAt());
    }
}
