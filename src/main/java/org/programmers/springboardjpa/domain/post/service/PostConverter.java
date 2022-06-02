package org.programmers.springboardjpa.domain.post.service;

import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostCreateRequestDto;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.programmers.springboardjpa.domain.post.domain.Post;
import org.programmers.springboardjpa.domain.user.domain.User;
import org.programmers.springboardjpa.domain.user.dto.UserDto.UserRequest;
import org.programmers.springboardjpa.domain.user.dto.UserDto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertPost(PostCreateRequestDto createRequest) {
        return Post.builder()
                .title(createRequest.title())
                .content(createRequest.content())
                .user(this.convertUser(createRequest.userDto()))
                .build();
    }

    private User convertUser(UserRequest userDto) {
        return User.builder()
                .name(userDto.name())
                .age(userDto.age())
                .hobby(userDto.hobby())
                .build();
    }

    public PostResponseDto convertPostDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                this.convertUserDto(post.getUser())
        );
    }

    private UserResponse convertUserDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getHobby()
        );
    }
}
