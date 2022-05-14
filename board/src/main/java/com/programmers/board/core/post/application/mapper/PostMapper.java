package com.programmers.board.core.post.application.mapper;

import com.programmers.board.core.post.application.dto.PostDto;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.user.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private UserMapper userMapper;

    public Post convertToEntity(PostDto requestPost){
        Post post = Post.builder()
                .title(requestPost.getTitle())
                .content(requestPost.getContent())
                .build();

        post.setUser(userMapper.convertToEntity(requestPost.getUserDto()));
        return post;
    }

    public PostDto convertToDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getContent())
                .content(post.getContent())
                .userDto(userMapper.convertToDto(post.getUser()))
                .build();
    }
}
