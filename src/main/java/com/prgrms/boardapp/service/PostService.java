package com.prgrms.boardapp.service;

import com.prgrms.boardapp.converter.PostConverter;
import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    private static final String NOT_FOUND_POST_ERR_MSG = "게시글 정보를 찾을 수 없습니다.";

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public PostDto findById(Long postId) {
        return postRepository.findById(postId)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_POST_ERR_MSG));
    }
}
