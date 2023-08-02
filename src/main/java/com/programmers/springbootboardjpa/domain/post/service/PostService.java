package com.programmers.springbootboardjpa.domain.post.service;

import com.programmers.springbootboardjpa.domain.post.domain.Post;
import com.programmers.springbootboardjpa.domain.post.domain.PostRepository;
import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.user.domain.User;
import com.programmers.springbootboardjpa.domain.user.domain.UserRepository;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto create(PostCreateRequestDto postCreateRequestDto) {
        User user = userRepository.findById(postCreateRequestDto.userId())
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        Post post = new Post(postCreateRequestDto.title(), postCreateRequestDto.content(), user);
        Post savedPost = postRepository.save(post);

        return PostResponseDto.from(savedPost);
    }

    @Transactional
    public PostResponseDto update(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

        post.updateTitle(postUpdateRequestDto.title());
        post.updateContent(postUpdateRequestDto.content());

        return PostResponseDto.from(post);
    }

    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
                .map(PostResponseDto::from)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
    }

    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponseDto::from);
    }
}
