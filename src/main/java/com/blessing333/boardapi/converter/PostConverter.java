package com.blessing333.boardapi.converter;

import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public PostInformation fromPost(Post post) {
        return PostInformation.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
