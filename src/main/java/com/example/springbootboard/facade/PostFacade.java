package com.example.springbootboard.facade;


import com.example.springbootboard.dto.PostCreateRequest;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.entity.Post;
import com.example.springbootboard.service.PostService;
import com.example.springbootboard.service.UserService;
import org.springframework.stereotype.Service;

import static com.example.springbootboard.dto.Converter.dtoToUser;

@Service
public class PostFacade {
    private final PostService postService;
    private final UserService userService;

    public PostFacade(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    public Post postCreateFacade(PostCreateRequest request) throws Exception {
        return postService.createPost(
                PostDto.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(dtoToUser(userService.findUserById(request.getUserId())))
                .build()
        );
    }
}
