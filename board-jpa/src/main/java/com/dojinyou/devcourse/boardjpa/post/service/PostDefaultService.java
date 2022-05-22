package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.post.entity.Post;
import com.dojinyou.devcourse.boardjpa.post.respository.PostRepository;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.dojinyou.devcourse.boardjpa.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostDefaultService implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostDefaultService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void create(long userId, PostCreateDto postCreateDto) {
        if (postCreateDto == null) {
            throw new IllegalArgumentException();
        }

        User user = userService.findById(userId);

        Post newPost = Post.builder()
                           .title(postCreateDto.getTitle())
                           .content(postCreateDto.getContent())
                           .user(user)
                           .build();

        postRepository.save(newPost);
    }
}
