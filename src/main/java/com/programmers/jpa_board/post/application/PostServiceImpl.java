package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.request.UpdatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import com.programmers.jpa_board.global.exception.NotFoundException;
import com.programmers.jpa_board.post.infra.PostRepository;
import com.programmers.jpa_board.post.util.PostConverter;
import com.programmers.jpa_board.user.application.UserProviderService;
import com.programmers.jpa_board.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {
    private static final String NOT_FOUND_POST = "게시글이 존재하지 않습니다.";

    private final PostRepository postRepository;
    private final UserProviderService userService;
    private final PostConverter converter;

    public PostServiceImpl(PostRepository postRepository, UserProviderService userService, PostConverter converter) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.converter = converter;
    }

    @Transactional
    @Override
    public PostResponse save(CreatePostRequest request) {
        Post post = converter.toEntity(request);
        User user = userService.getOne(request.userId());
        post.addUser(user);

        Post saved = postRepository.save(post);

        return converter.toDto(saved);
    }

    @Override
    public PostResponse getOne(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));

        return converter.toDto(post);
    }

    @Override
    public Page<PostResponse> getPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::toDto);
    }

    @Transactional
    @Override
    public PostResponse update(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));

        post.update(request.title(), request.content());

        return converter.toDto(post);
    }
}
