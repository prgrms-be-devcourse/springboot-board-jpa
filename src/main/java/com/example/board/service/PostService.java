package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import org.apache.ibatis.javassist.NotFoundException;
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

    @Transactional
    public PostDto getOnePost(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("찾는 게시글이 없습니다."));
    }

    @Transactional
    public Page<PostDto> getAllPostByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public PostDto updatePost(Long id, String title, String content) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("찾는 게시글이 없습니다."));

        post.updatePost(title, content);
        return postConverter.convertPostDto(post);
    }

    @Transactional
    public PostDto updatePost(Long id, PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("찾는 게시글이 없습니다."));

        post.updatePost(postDto.getTitle(), postDto.getContent());
        return postConverter.convertPostDto(post);
    }

    @Transactional
    public PostDto writePost(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        postRepository.save(post);
        return postConverter.convertPostDto(post);
    }
}
