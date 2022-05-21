package com.devcourse.springjpaboard.post.service;

import com.devcourse.springjpaboard.error.NotFoundException;
import com.devcourse.springjpaboard.model.post.Post;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.converter.PostConverter;
import com.devcourse.springjpaboard.post.repository.PostRepository;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.springjpaboard.error.ErrorMessage.NOT_FOUND_POST;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostConverter postConverter;

    public PostServiceImpl(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Override
    public PostResponse save(CreatePostRequest createPostRequest) {
        Post post = postConverter.convertPostRequest(createPostRequest);
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
