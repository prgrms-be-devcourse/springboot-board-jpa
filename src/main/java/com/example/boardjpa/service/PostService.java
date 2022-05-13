package com.example.boardjpa.service;

import com.example.boardjpa.domain.Post;
import com.example.boardjpa.domain.User;
import com.example.boardjpa.dto.CreatePostRequestDto;
import com.example.boardjpa.dto.PostResponseDto;
import com.example.boardjpa.dto.UpdatePostRequestDto;
import com.example.boardjpa.repository.PostRepository;
import com.example.boardjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

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
                                () -> new RuntimeException("해당 id의 post가 존재하지 않습니다.")
                        )
        );
    }

    public Long createPost(CreatePostRequestDto createPostRequestDto) {
        User user = userRepository
                .findById(
                        createPostRequestDto
                                .getUserId()
                ).orElseThrow(
                        () -> new RuntimeException("해당 id의 user가 존재하지 않습니다.")
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
                .orElseThrow(() -> new RuntimeException("해당 id의 post가 존재하지 않습니다.")
                );
        post.setContent(updatePostRequestDto.getContent());
    }
}
