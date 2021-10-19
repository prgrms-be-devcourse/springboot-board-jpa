package com.kdt.post.service;

import com.kdt.domain.post.Post;
import com.kdt.domain.user.User;
import com.kdt.post.dto.PostSaveDto;
import com.kdt.post.dto.PostViewDto;
import com.kdt.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConvertor {

    public Post convertPostSaveDtoToPost(PostSaveDto postSaveDto) {
        return Post.builder()
                .id(postSaveDto.getId())
                .title(postSaveDto.getTitle())
                .content(postSaveDto.getContent())
                .user(convertUserDtoToUser(postSaveDto.getUserDto()))
                .build();
    }

    private User convertUserDtoToUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getBirthYear())
                .build();
        return user;
    }

    public PostViewDto convertPostToPostViewDto(Post post) {
        PostViewDto postViewDto = new PostViewDto();
        postViewDto.setId(post.getId());
        postViewDto.setTitle(post.getTitle());
        postViewDto.setContent(post.getContent());
        postViewDto.setUserDto(convertUserToUserDto(post.getUser()));
        return postViewDto;
    }

    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setBirthYear(user.getAge());
        return userDto;
    }
}
