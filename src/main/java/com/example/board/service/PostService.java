package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.CreatePostRequest;
import com.example.board.dto.request.PostSearchCondition;
import com.example.board.dto.request.UpdatePostRequest;
import com.example.board.dto.response.PostResponse;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(CreatePostRequest requestDto) {
        final User user = userRepository.findById(requestDto.authorId()).orElseThrow(() -> new NoSuchElementException("유저가 없음"));
        final Post post = postRepository.save(PostConverter.toPost(requestDto, user));
        return post.getId();
    }

    public Page<PostResponse> getPosts(PostSearchCondition condition, Pageable pageable) {
        return postRepository.findAll(condition, pageable).map(PostConverter::toPostResponse);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("게시글이 없음"));
        return PostConverter.toPostResponse(post);
    }

    public void updatePost(Long id, UpdatePostRequest requestDto) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("게시글이 없음"));
        post.update(requestDto.title(), requestDto.content());
    }

    public void deletePost(Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("게시글이 없음"));
        postRepository.delete(post);
    }
}
