package com.example.demo.service;

import com.example.demo.converter.PostConverter;
import com.example.demo.domain.Post;
import com.example.demo.dto.PostDto;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional(readOnly = true)
    public PostDto findOne(Long postId) throws PostNotFoundException {
        return postRepository.findById(postId)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(postId)));
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAllByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public PostDto updateTitleAndContent(PostDto postDto, Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(postId)));

        post.changeTitleAndContent(postDto.title(), postDto.content());
        return postConverter.convertPostDto(post);
    }
}
