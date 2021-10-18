package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertFromDtoToPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertFromPostToDto);
    }

    @Transactional
    public PostDto findById(Long id) throws NotFoundException {
        return postRepository.findById(id).map(postConverter::convertFromPostToDto).orElseThrow(() -> new NotFoundException("Post Not Found"));
    }

    @Transactional
    public Long editPost(PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new NotFoundException("Post Not Found"));
        post.updateTitle(postDto.getTitle());
        post.updateContent(postDto.getContent());
        return postRepository.save(post).getId();
    }
}
