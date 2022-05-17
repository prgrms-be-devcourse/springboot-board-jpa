package com.example.boardjpa.service;

import com.example.boardjpa.domain.Post;
import com.example.boardjpa.domain.User;
import com.example.boardjpa.dto.*;
import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.RecordNotFoundException;
import com.example.boardjpa.repository.PostRepository;
import com.example.boardjpa.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostsResponseDto getPosts(Pageable pageable) {
        List<PostResponseDto> posts = postRepository
                .findAll(pageable)
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        return new PostsResponseDto(pageable.getPageNumber(), pageable.getPageSize(), posts);
    }

    public PostResponseDto getPostById(Long postId) {
        return new PostResponseDto(
                postRepository
                        .findById(postId)
                        .orElseThrow(
                                () -> new RecordNotFoundException(
                                        "해당 id의 post가 존재하지 않습니다."
                                        , ErrorCode.POST_NOT_FOUND)
                        )
        );
    }

    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto) {
        User user = userRepository
                .findById(
                        createPostRequestDto
                                .getUserId()
                ).orElseThrow(
                        () -> new RecordNotFoundException(
                                "해당 id의 user가 존재하지 않습니다."
                                , ErrorCode.USER_NOT_FOUND)
                );
        Long postId = postRepository.save(
                new Post(createPostRequestDto.getTitle()
                        , createPostRequestDto.getContent()
                        , user)
        ).getId();
        return new CreatePostResponseDto(postId);
    }

    public void updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new RecordNotFoundException(
                        "해당 id의 post가 존재하지 않습니다."
                        , ErrorCode.POST_NOT_FOUND)
                );
        post.setContent(updatePostRequestDto.getContent());
    }
}
