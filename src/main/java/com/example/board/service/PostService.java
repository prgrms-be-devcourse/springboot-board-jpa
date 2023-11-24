package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.post.CreatePostRequest;
import com.example.board.dto.request.post.PostSearchCondition;
import com.example.board.dto.request.post.UpdatePostRequest;
import com.example.board.dto.response.PageResponse;
import com.example.board.dto.response.PostResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.repository.post.PostRepository;
import com.example.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(CreatePostRequest requestDto) {
        final User user = userRepository.findById(requestDto.authorId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        final Post post = postRepository.save(PostConverter.toPost(requestDto, user));
        return post.getId();
    }

    public PageResponse<PostResponse> getPosts(PostSearchCondition condition, Pageable pageable) {
        Page<PostResponse> posts = postRepository.findAll(condition, pageable).map(PostConverter::toPostResponse);
        return PageResponse.of(posts);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return PostConverter.toPostResponse(post);
    }

    public void updatePost(Long id, UpdatePostRequest requestDto) {
        final Post post = postRepository.findByIdWithAuthor(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!isAuthor(post, requestDto.authorId()))
            throw new CustomException(ErrorCode.AUTHOR_NOT_MATCH);

        post.update(requestDto.title(), requestDto.content());
    }

    public void deletePost(Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }

    private Boolean isAuthor(Post post, Long authorId) {
        return post.getAuthor().getId().equals(authorId);
    }
}
