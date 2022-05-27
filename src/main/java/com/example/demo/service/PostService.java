package com.example.demo.service;

import com.example.demo.converter.PostConverter;
import com.example.demo.domain.Post;
import com.example.demo.dto.PostDto;
import com.example.demo.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional(readOnly = true)
    public PostDto findOne(Long postId) throws NotFoundException {
        return postRepository.findById(postId)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("찾는 값이 없습니다."));
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
    public PostDto updateTitleAndContent(PostDto postDto, Long postId) throws NotFoundException {
        Optional<Post> findPost = postRepository.findById(postId);
        Post post = findPost.orElseThrow(() -> new NotFoundException("없는 데이터는 수정 할 수 없습니다."));

        post.changeTitleAndContent(postDto.getTitle(), postDto.getContent());
        return postConverter.convertPostDto(post);
    }
}
