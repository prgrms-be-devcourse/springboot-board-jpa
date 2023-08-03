package com.prgms.jpaBoard.domain.post.application;

import com.prgms.jpaBoard.domain.post.Post;
import com.prgms.jpaBoard.domain.post.PostRepository;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponse;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponses;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostSaveRequest;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostUpdateRequest;
import com.prgms.jpaBoard.domain.user.User;
import com.prgms.jpaBoard.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final String USER_NOT_FOUND_MSG = "유저를 찾을 수 없습니다.";
    private final String POST_NOT_FOUND_MSG = "게시글을 찾을 수 없습니다.";

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long post(PostSaveRequest postSaveRequest) {
        User findUser = userRepository.findById(postSaveRequest.userId())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND_MSG));

        Post post = PostMapper.from(postSaveRequest, findUser);
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    public PostResponse readOne(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND_MSG));

        return new PostResponse(findPost);
    }

    public PostResponses readAll(Pageable pageable) {
        List<PostResponse> postResponses = postRepository.findAll(pageable)
                .stream()
                .map(PostResponse::new)
                .toList();

        return new PostResponses(postResponses);
    }

    @Transactional
    public PostResponse update(Long postId, PostUpdateRequest postUpdateRequest) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(POST_NOT_FOUND_MSG));

        findPost.updatePost(postUpdateRequest.title(), postUpdateRequest.content());

        return new PostResponse(findPost);
    }
}
