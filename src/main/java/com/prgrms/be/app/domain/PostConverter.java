package com.prgrms.be.app.domain;

import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post covertToPost(PostDTO.CreateRequest postCreateDto, User user) {
        return new Post(postCreateDto.title(), postCreateDto.content(), user);
    }
}
