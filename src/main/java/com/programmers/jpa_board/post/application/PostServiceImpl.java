package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.global.converter.BoardConverter;
import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import com.programmers.jpa_board.post.infra.PostRepository;
import com.programmers.jpa_board.user.application.UserProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService{
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
}
