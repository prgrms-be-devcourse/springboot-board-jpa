package com.prgrms.boardapp.service;

import com.prgrms.boardapp.dto.PostRequest;
import com.prgrms.boardapp.dto.PostResponse;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.prgrms.boardapp.constant.PostErrMsg.NOT_FOUND_POST_ERR_MSG;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final Converter converter;

    public PostService(PostRepository postRepository, Converter converter) {
        this.postRepository = postRepository;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long postId) {
        return postRepository.findById(postId)
                .map(converter::convertPostResponse)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_POST_ERR_MSG.getMessage()));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::convertPostResponse);
    }

    @Transactional
    public Long save(PostRequest postDto) {
        Post post = converter.convertPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public void update(Long postId, PostRequest postRequest) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_POST_ERR_MSG.getMessage()));
        savedPost.update(postRequest.getTitle(), postRequest.getContent());
    }
}
