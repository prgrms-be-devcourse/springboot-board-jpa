package com.programmers.domain.post.application;

import com.programmers.domain.post.application.converter.PostConverter;
import com.programmers.domain.post.entity.Post;
import com.programmers.domain.post.infra.PostRepository;
import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import com.programmers.domain.user.entity.User;
import com.programmers.domain.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.userId())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        Post post = postConverter.convertPost(postDto, user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Override
    public PostDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND));
        return postConverter.convertPostDto(post);
    }

    @Override
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Override
    @Transactional
    public PostDto updatePost(PostUpdateDto postUpdateDto, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND));
        post.changeTitle(postUpdateDto.title());
        post.changeContent(postUpdateDto.content());
        return postConverter.convertPostDto(post);
    }
}
