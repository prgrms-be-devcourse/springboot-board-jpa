package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.global.converter.BoardConverter;
import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.request.UpdatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import com.programmers.jpa_board.post.exception.NotFoundPostException;
import com.programmers.jpa_board.post.infra.PostRepository;
import com.programmers.jpa_board.user.application.UserProviderService;
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
    private final BoardConverter converter;

    public PostServiceImpl(PostRepository postRepository, UserProviderService userService, BoardConverter converter) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.converter = converter;
    }

    @Transactional
    @Override
    public PostResponse create(CreatePostRequest request) {
        Post post = converter.createPostToPost(request);
        post.setUser(userService.getUser(request.getUserId()));

        Post saved = postRepository.save(post);

        return converter.postToDto(saved);
    }

    @Override
    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST));

        return converter.postToDto(post);
    }

    @Override
    public Page<PostResponse> findPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::postToDto);
    }

    @Transactional
    @Override
    public PostResponse update(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST));

        post.update(request.getTitle(), request.getContent());

        return converter.postToDto(post);
    }
}
