package com.ys.board.domain.post.service;

import com.ys.board.domain.post.model.Post;
import com.ys.board.domain.post.api.PostUpdateRequest;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.api.PostCreateResponse;
import com.ys.board.domain.post.api.PostResponse;
import com.ys.board.domain.post.api.PostResponses;
import com.ys.board.domain.user.model.User;
import com.ys.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceFacade {

    private final UserService userService;

    private final PostService postService;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request) {

        User findUser = userService.findById(request.getUserId());

        Post post = postService.createPost(findUser, request);

        return new PostCreateResponse(post.getId());
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long postId) {
        Post post = postService.findById(postId);
        return new PostResponse(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request) {
        postService.updatePost(postId, request);
    }

    @Transactional(readOnly = true)
    public PostResponses findAllPostsByIdCursorBased(Long cursorId, Pageable pageable) {
        return postService.findAllByIdCursorBased(cursorId, pageable);
    }

}
