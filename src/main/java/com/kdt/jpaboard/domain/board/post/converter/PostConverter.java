package com.kdt.jpaboard.domain.board.post.converter;

import com.kdt.jpaboard.domain.board.post.Posts;
import com.kdt.jpaboard.domain.board.post.dto.CreatePostDto;
import com.kdt.jpaboard.domain.board.post.dto.PostDto;
import com.kdt.jpaboard.domain.board.user.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PostConverter {

    @Autowired
    UserConverter userConverter;

    public Posts convertPost(PostDto postDto) {
        Posts posts = Posts.builder()
                .id(postDto.getPostId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        posts.updateUserInfo(userConverter.convertUser(postDto.getUserDto()));
//        posts.setCreatedBy(postDto.getUserDto().getName());

        return posts;
    }

    public Posts convertCreatePost(CreatePostDto postDto) {
        Posts posts = Posts.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();


        posts.updateUserInfo(userConverter.convertUser(postDto.getUserDto()));
//        posts.setCreatedBy(postDto.getUserDto().getName());

        return posts;
    }


    public PostDto convertReadPostDto(Posts posts) {
        return PostDto.builder()
                .postId(posts.getId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .userDto(userConverter.convertUserDto(posts.getUser()))
                .build();
    }


}
