package com.programmers.post.application;

import com.programmers.post.converter.PostConverter;
import com.programmers.post.domain.Post;
import com.programmers.post.dto.PostDto;
import com.programmers.post.dto.PostUpdateDto;
import com.programmers.post.infra.PostRepository;
import com.programmers.user.domain.User;
import com.programmers.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private static final String USER_NOT_FOUND = "유저를 찾을 수 없습니다.";
    private static final String POST_NOT_FOUND = "게시글을 찾을 수 없습니다.";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    @Transactional
    public Long createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.userId())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        Post post = postConverter.convertPost(postDto, user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public PostDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND));
        return postConverter.convertPostDto(post);
    }

    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> postConverter.convertPostDto(post));
    }

    @Transactional
    public PostDto updatePost(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.postId())
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND));
        post.changeTitle(postUpdateDto.title());
        post.changeContent(postUpdateDto.content());
        return postConverter.convertPostDto(post);
    }
}
