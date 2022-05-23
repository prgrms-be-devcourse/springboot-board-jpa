package com.devcourse.springjpaboard.post.service;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.model.post.Post;
import com.devcourse.springjpaboard.model.user.User;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.converter.PostConverter;
import com.devcourse.springjpaboard.post.repository.PostRepository;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.springjpaboard.exception.ExceptionMessage.NOT_FOUND_POST;
import static com.devcourse.springjpaboard.exception.ExceptionMessage.NOT_FOUND_USER;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostConverter postConverter;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postConverter = postConverter;
    }

    @Override
    public PostResponse save(CreatePostRequest createPostRequest) {
        User user = userRepository.findById(createPostRequest.userId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        Post post = postConverter.convertPostRequest(createPostRequest, user);
        postRepository.save(post);
        return postConverter.convertPostResponse(post);
    }

    @Override
    public PostResponse findOne(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertPostResponse)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));
    }

    @Override
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostResponse);
    }

    @Override
    public PostResponse update(Long id, UpdatePostRequest updatePostRequest) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatePostRequest.title());
                    post.setContent(updatePostRequest.content());
                    return post;
                })
                .map(postConverter::convertPostResponse)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));
    }


}
