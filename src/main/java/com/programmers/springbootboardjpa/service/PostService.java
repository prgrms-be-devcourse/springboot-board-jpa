package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.dto.PostServiceRequestDto;
import com.programmers.springbootboardjpa.dto.PostServiceResponseDto;
import com.programmers.springbootboardjpa.entity.Post;
import com.programmers.springbootboardjpa.entity.User;
import com.programmers.springbootboardjpa.exception.PostNotFoundException;
import com.programmers.springbootboardjpa.exception.UserNotFoundException;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceResponseDto create(PostServiceRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(user)
                .build();
        return PostServiceResponseDto.of(postRepository.save(post));
    }

    public void update(Long postId, PostServiceRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.updateTitle(requestDto.getTitle());
        post.updateContent(requestDto.getContent());
    }

    @Transactional(readOnly = true)
    public PostServiceResponseDto findPostById(Long postId) {
        return postRepository.findById(postId)
                .map(PostServiceResponseDto::of).orElseThrow(PostNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<PostServiceResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostServiceResponseDto::of);
    }

}
