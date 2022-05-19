package com.pppp0722.boardjpa.post.service;

import com.pppp0722.boardjpa.domain.post.Post;
import com.pppp0722.boardjpa.domain.post.PostRepository;
import com.pppp0722.boardjpa.post.converter.PostConverter;
import com.pppp0722.boardjpa.post.dto.PostDto;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostServiceImpl(PostRepository postRepository,
        PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Override
    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        Post entity = postRepository.save(post);

        return entity.getId();
    }

    @Override
    @Transactional
    public PostDto findById(Long id) {
        return postRepository.findById(id)
            .map(postConverter::convertPostDto)
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postConverter::convertPostDto);
    }

    @Override
    @Transactional
    public PostDto update(PostDto postDto) {
        Post entity = postRepository.findById(postDto.getId())
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        entity.setCreatedAt(postDto.getCreatedAt());
        entity.setCreatedBy(postDto.getCreatedBy());
        entity.setId(postDto.getId());
        entity.setTitle(postDto.getTitle());
        entity.setContent(postDto.getContent());
        entity.setUser(postConverter.convertUser(postDto.getUserDto()));

        return postDto;
    }
}
