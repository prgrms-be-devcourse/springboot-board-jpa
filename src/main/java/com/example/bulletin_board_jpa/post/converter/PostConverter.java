package com.example.bulletin_board_jpa.post.converter;

import com.example.bulletin_board_jpa.post.Post;
import com.example.bulletin_board_jpa.post.dto.PostRequestDto;
import com.example.bulletin_board_jpa.post.dto.PostResponseDto;
import com.example.bulletin_board_jpa.user.User;
import com.example.bulletin_board_jpa.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    // dto -> entity
    public Post convertToPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setUser(convertToUser(postRequestDto.getUserDto()));
        return post;
    }

    private User convertToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        return user;
    }

    // entity -> postResponseDto
    public PostResponseDto convertToPostResponseDto(Post post) {
        String title = post.getTitle();
        String content = post.getContent();

        return new PostResponseDto(title, content);
    }
}
