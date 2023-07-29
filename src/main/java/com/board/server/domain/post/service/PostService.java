package com.board.server.domain.post.service;

import com.board.server.domain.post.dto.request.CreatePostRequest;
import com.board.server.domain.post.dto.request.UpdatePostRequest;
import com.board.server.domain.post.dto.response.PageInfo;
import com.board.server.domain.post.dto.response.PostResponse;
import com.board.server.domain.post.dto.response.PostsResponse;
import com.board.server.domain.post.entity.Post;
import com.board.server.domain.post.entity.PostRepository;
import com.board.server.domain.user.entity.User;
import com.board.server.domain.user.entity.UserRepository;
import com.board.server.global.exception.Error;
import com.board.server.global.exception.model.BadRequestException;
import com.board.server.global.exception.model.NotFoundException;
import com.board.server.global.exception.model.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final int PAGE_SIZE = 10;

    @Transactional
    public PostResponse createPost(CreatePostRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_USER_EXCEPTION, Error.NOT_FOUND_USER_EXCEPTION.getMessage()));

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
        post.setCreatedBy(user.getName());
        postRepository.save(post);

        return PostResponse.of(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedBy(),
                post.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public PostsResponse getAllPost(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE);

        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageRequest);

        List<PostResponse> postResponses = posts.stream()
                .map(post -> PostResponse.of(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedBy(),
                        post.getCreatedAt()
                )).toList();

        PageInfo pageInfo = PageInfo.of(posts.getTotalPages(), posts.getNumber() + 1, posts.getTotalPages() == posts.getNumber() + 1);

        return PostsResponse.of(postResponses, pageInfo);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_POST_EXCEPTION, Error.NOT_FOUND_POST_EXCEPTION.getMessage()));

        return PostResponse.of(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedBy(),
                post.getCreatedAt()
        );
    }

    @Transactional
    public PostResponse updatePost(UpdatePostRequest request, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_POST_EXCEPTION, Error.NOT_FOUND_POST_EXCEPTION.getMessage()));

        if (post.getUser().getUserId() != userId) {
            throw new UnauthorizedException(Error.UPDATE_POST_UNAUTHORIZED, Error.UPDATE_POST_UNAUTHORIZED.getMessage());
        }

        if (request.title() != null) {
            post.updateTitle(request.title());
        }

        if (request.content() != null) {
            post.updateContent(request.content());
        }

        return PostResponse.of(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedBy(),
                post.getCreatedAt()
        );
    }
}
