package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
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

    @Transactional(readOnly = true)
    public PostResponseDto getOnePost(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostResponseDto)
                .orElseThrow(() -> new NotFoundException("찾는 게시글이 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPostByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostResponseDto);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, String title, String content) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("찾는 게시글이 없습니다."));

        post.updatePost(title, content);
        return postConverter.convertPostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("찾는 게시글이 없습니다."));

        post.updatePost(postRequestDto.title(), postRequestDto.content());
        return postConverter.convertPostResponseDto(post);
    }

    @Transactional
    public PostResponseDto writePost(PostRequestDto postRequestDto) {
        Post post = postConverter.convertPost(postRequestDto);
        postRepository.save(post);
        return postConverter.convertPostResponseDto(post);
    }
}
