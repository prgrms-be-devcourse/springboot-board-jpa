package com.prgrms.board.domain.post.service;

import static com.prgrms.board.global.common.ErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.request.PostUpdateRequest;
import com.prgrms.board.domain.post.dto.request.PostsRequest;
import com.prgrms.board.domain.post.dto.response.PostDetailResponse;
import com.prgrms.board.domain.post.dto.response.PostResponse;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.exception.PostNotFoundException;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.service.UserService;
import com.prgrms.board.global.common.PageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PageResponse<Post> getPosts(PostsRequest request) {
        Page<Post> posts = postRepository.findAll(request.toPageable());
        return PageResponse.from(posts);
    }

    public PostDetailResponse getPost(Long postId) {
        Post post = findPostOrThrow(postId);
        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        User user = userService.findUserOrThrow(request.userId());
        Post post = postRepository.save(request.toEntity(user));
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = findPostOrThrow(postId);
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }

    private Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(NO_POST));
    }
}
