package com.prgrms.board.post.service;

import com.prgrms.board.post.domain.Post;
import com.prgrms.board.post.domain.PostRepository;
import com.prgrms.board.post.exception.PostNotFoundException;
import com.prgrms.board.post.service.dto.*;
import com.prgrms.board.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final PostServiceConverter converter;

    public PostService(PostRepository postRepository, UserService userService, PostServiceConverter converter) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.converter = converter;
    }

    @Transactional
    public PostShortResult save(PostDetailedParam postDetailedParam) {
        userService.validateUserExistence(postDetailedParam.userId());
        Post savedPost = postRepository.save(converter.toPost(postDetailedParam));
        return converter.toPostShortResult(savedPost);
    }

    public PostDetailedResult findById(Long id) {
        Post retrievedPost = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post가 존재하지 않습니다."));
        return converter.toPostDetailedResult(retrievedPost);

    }

    public PostDetailedResults findAllWithPagination(Pageable pageable) {
        Page<Post> retrievedPosts = postRepository.findAll(pageable);
        if (retrievedPosts.getSize() == 0) {
            throw new PostNotFoundException("Post가 존재하지 않습니다.");
        }
        return converter.toPostDetailedResults(retrievedPosts);
    }

    @Transactional
    public PostShortResult update(Long id, PostDetailedParam postDetailedParam) {
        userService.validateUserExistence(postDetailedParam.userId());
        Post retrievedPost = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post가 존재하지 않습니다."));
        changePost(retrievedPost, postDetailedParam);

        Post updatedUser = postRepository.save(retrievedPost);
        return converter.toPostShortResult(updatedUser);
    }

    private void changePost(Post retrievedPost, PostDetailedParam postDetailedParam) {
        retrievedPost.changeTitle(postDetailedParam.title());
        retrievedPost.changeContent(postDetailedParam.content());
    }
}
