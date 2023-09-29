package com.blackdog.springbootBoardJpa.domain.post.service;

import com.blackdog.springbootBoardJpa.domain.post.model.Post;
import com.blackdog.springbootBoardJpa.domain.post.repository.PostRepository;
import com.blackdog.springbootBoardJpa.domain.post.service.converter.PostServiceConverter;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostCreateRequest;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponse;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponses;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostUpdateRequest;
import com.blackdog.springbootBoardJpa.domain.user.model.User;
import com.blackdog.springbootBoardJpa.domain.user.repository.UserRepository;
import com.blackdog.springbootBoardJpa.global.exception.PermissionDeniedException;
import com.blackdog.springbootBoardJpa.global.exception.PostNotFoundException;
import com.blackdog.springbootBoardJpa.global.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.blackdog.springbootBoardJpa.global.response.ErrorCode.*;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostServiceConverter converter;

    public PostService(
            final PostRepository postRepository,
            final UserRepository userRepository,
            final PostServiceConverter converter
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Transactional
    public PostResponse savePost(Long userId, @Valid PostCreateRequest request) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));

        Post post = converter.toEntity(request, user);

        return converter.toResponse(
                postRepository.save(post));
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, @Valid PostUpdateRequest dto) {
        Post targetPost = postRepository
                .findById(postId)
                .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST));

        boolean isOwner = Objects.equals(targetPost.getUser().getId(), userId);
        if (!isOwner) {
            throw new PermissionDeniedException(PERMISSION_DENIED);
        }

        targetPost.changePost(dto.title(), dto.content());
        return converter.toResponse(targetPost);
    }

    @Transactional
    public void deletePostById(Long userId, Long id) {
        postRepository.deleteByIdAndUserId(id, userId);
    }

    public PostResponses findAllPosts(Pageable pageable) {
        return converter.toResponses(
                postRepository.findAll(pageable));
    }

    public PostResponse findPostById(Long id) {
        return converter.toResponse(
                postRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException(NOT_FOUND_POST)));
    }

    public PostResponses findPostsByUserId(Long userId, Pageable pageable) {
        return converter.toResponses(
                postRepository.findPostsByUserId(userId, pageable));
    }

}
