package com.devco.jpaproject.service.converter;

import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.controller.dto.UserResponseDto;
import com.devco.jpaproject.domain.Post;
import com.devco.jpaproject.domain.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    /**
     * dto를 entity로 변환
     */
    public Post toPostEntity(PostRequestDto dto, User writer) {
        var post = Post.builder()
                .content(dto.getContent())
                .title(dto.getTitle())
                .build();

        post.setWriter(writer);

        return post;
    }

    /**
     * 영속 상태 entity를 dto 객체로 변환
     */
    public PostResponseDto toPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .content(post.getContent())
                .title(post.getTitle())
                .postId(post.getId())
                .writerDto(this.toUserResponseDto(post.getWriter()))
                .build();
    }

    public UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .age(user.getAge())
                .hobby(user.getHobby())
                .name(user.getName())
                .build();
    }

    public User toUserEntity(UserRequestDto userDto) {
        return User.builder()
                .age(userDto.getAge())
                .name(userDto.getName())
                .hobby(userDto.getHobby())
                .build();
    }
}
