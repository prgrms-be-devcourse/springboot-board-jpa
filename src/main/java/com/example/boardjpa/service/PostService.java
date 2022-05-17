package com.example.boardjpa.service;

import com.example.boardjpa.domain.Post;
import com.example.boardjpa.domain.User;
import com.example.boardjpa.dto.CreatePostRequestDto;
import com.example.boardjpa.dto.PostResponseDto;
import com.example.boardjpa.dto.UpdatePostRequestDto;
import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.RecordNotFoundException;
import com.example.boardjpa.repository.PostRepository;
import com.example.boardjpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Page<PostResponseDto> getPosts(Pageable pageable) {
        return postRepository
                .findAll(pageable)
                .map(PostResponseDto::new);
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

    public Long createPost(CreatePostRequestDto createPostRequestDto) {
        User user = userRepository
                .findById(
                        createPostRequestDto
                                .getUserId()
                ).orElseThrow(
                        () -> new RecordNotFoundException(
                                "해당 id의 user가 존재하지 않습니다."
                                , ErrorCode.USER_NOT_FOUND)
                );
        return postRepository.save(
                new Post(createPostRequestDto.getTitle()
                        , createPostRequestDto.getContent()
                        , user)
        ).getId();
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
