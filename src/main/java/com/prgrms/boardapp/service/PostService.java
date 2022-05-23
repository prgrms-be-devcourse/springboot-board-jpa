package com.prgrms.boardapp.service;

import com.prgrms.boardapp.converter.Converter;
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
    private final Converter converter;

    private static final String NOT_FOUND_POST_ERR_MSG = "게시글 정보를 찾을 수 없습니다.";

    public PostService(PostRepository postRepository, Converter converter) {
        this.postRepository = postRepository;
        this.converter = converter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = converter.convertPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::convertPostDto);
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long postId) {
        return postRepository.findById(postId)
                .map(converter::convertPostDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_POST_ERR_MSG));
    }

    @Transactional
    public PostDto update(PostDto postDto) {
        Post savedPost = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_POST_ERR_MSG));
        savedPost.changeTitle(postDto.getTitle());
        savedPost.changeContent(postDto.getContent());
        return postDto;
    }
}
