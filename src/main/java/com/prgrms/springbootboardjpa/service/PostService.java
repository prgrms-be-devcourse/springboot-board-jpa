package com.prgrms.springbootboardjpa.service;

import com.prgrms.springbootboardjpa.dto.PostDto;
import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.exception.NotFoundException;
import com.prgrms.springbootboardjpa.repository.PostRepository;
import com.prgrms.springbootboardjpa.dto.DtoMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final DtoMapper dtoConverter;

    public PostService(PostRepository postRepository, DtoMapper dtoConverter) {
        this.postRepository = postRepository;
        this.dtoConverter = dtoConverter;
    }

    @Transactional
    public PostDto save(PostDto postDto) {
        Post post = dtoConverter.convertPost(postDto);
        post.setCreatedBy(postDto.getUserDto().getName());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post entity = postRepository.save(post);

        return dtoConverter.convertPostDto(entity);
    }

    @Transactional
    public PostDto getOne(long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(dtoConverter::convertPostDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Page<PostDto> getAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(dtoConverter::convertPostDto);
    }

    @Transactional
    public PostDto update(long id, PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        Post entity = postRepository.save(post);

        return dtoConverter.convertPostDto(entity);
    }

    @Transactional
    public void delete(long id) throws NotFoundException {
        try {
            postRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }
}
