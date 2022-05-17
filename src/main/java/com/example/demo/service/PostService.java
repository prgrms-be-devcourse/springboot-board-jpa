package com.example.demo.service;

import com.example.demo.converter.PostConverter;
import com.example.demo.domain.Post;
import com.example.demo.dto.PostDto;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.apache.ibatis.javassist.NotFoundException;
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
}
