package com.example.board.domain.post.service;

import static com.example.board.domain.post.dto.PostDto.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.domain.user.entity.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.validator.EntityValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final EntityValidator entityValidator = new EntityValidator();
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Page<SinglePostResponse> pagingPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto.SinglePostResponse::toResponse);
    }

    @Override
    public SinglePostResponse getPost(long postId) {
        Optional<Post> mayBePost = postRepository.findById(postId);
        Post findPost = entityValidator.validateOptionalExists(mayBePost);
        return SinglePostResponse.toResponse(findPost);
    }

    @Override
    @Transactional
    public SinglePostResponse post(CreatePostRequest createPostRequest) {
        Optional<User> mayBeUser = userRepository.findById(createPostRequest.userId());
        User findUser = entityValidator.validateOptionalExists(mayBeUser);

        Post post = Post.builder()
                .title(createPostRequest.title())
                .content(createPostRequest.content())
                .user(findUser)
                .build();

        Post savedPost = postRepository.save(post);

        return SinglePostResponse.toResponse(savedPost);
    }

    @Override
    @Transactional
    public SinglePostResponse updatePost(long postId, UpdatePostRequest updatePostRequest) {
        Optional<Post> mayBePost = postRepository.findById(postId);
        Post findPost = entityValidator.validateOptionalExists(mayBePost);
        findPost.updatePost(updatePostRequest);
        return SinglePostResponse.toResponse(findPost);
    }
}
