package com.example.springbootboard.service;


import com.example.springbootboard.converter.DtoConverter;
import com.example.springbootboard.dto.PostRequestDto;
import com.example.springbootboard.dto.PostResponseDto;
import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.exception.error.NotFoundException;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    public PostService(PostRepository postRepository, UserRepository userRepository, DtoConverter dtoConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
    }

    @Transactional
    public Long insert(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId()).
                orElseThrow(() -> {
                    throw new NotFoundException("유저를 찾을 수 없습니다.");
                });
        Post post = dtoConverter.convertPost(postRequestDto);
        user.addPost(post);
        post = postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
                .map(post -> dtoConverter.convertPostResponseDto(post))
                .orElseThrow(() -> {
                    throw new NotFoundException("게시글을 찾을 수 없습니다.");
                });
    }

    @Transactional
    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(post -> dtoConverter.convertPostResponseDto(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> {
                    throw new NotFoundException("게시글을 찾을 수 없습니다.");
                });
        post.changeTitle(postRequestDto.getTitle());
        post.changeContent(postRequestDto.getContent());

        return dtoConverter.convertPostResponseDto(post);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
