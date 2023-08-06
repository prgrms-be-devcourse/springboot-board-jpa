package com.programmers.domain.post.application;

import com.programmers.domain.post.application.converter.PostConverter;
import com.programmers.domain.post.entity.Post;
import com.programmers.domain.post.infra.PostRepository;
import com.programmers.domain.post.ui.dto.PostCreateDto;
import com.programmers.domain.post.ui.dto.PostResponseDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import com.programmers.domain.user.entity.User;
import com.programmers.domain.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private static final String USER_NOT_FOUND = "유저를 찾을 수 없습니다.";
    private static final String POST_NOT_FOUND = "게시글을 찾을 수 없습니다.";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    @Override
    @Transactional
    public Long createPost(PostCreateDto postCreateDto) {
        User user = userRepository.findById(postCreateDto.userId())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        Post post = postConverter.convertPost(postCreateDto, user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Override
    public PostResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND));
        return postConverter.convertEntityToPostResponseDto(post);
    }

    @Override
    public List<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .stream()
                .map(postConverter::convertEntityToPostResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(PostUpdateDto postUpdateDto, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND));
        post.changeTitleAndContent(postUpdateDto.title(), postUpdateDto.content());
        return postConverter.convertEntityToPostResponseDto(post);
    }
}
