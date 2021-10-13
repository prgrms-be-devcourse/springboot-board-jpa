package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.domain.Post;
import com.devco.jpaproject.domain.User;
import com.devco.jpaproject.repository.PostRepository;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.converter.Converter;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final Converter converter;

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll(){
        return postRepository.findAll().stream()
                .map(converter::toPostResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByPages(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(converter::toPostResponseDto);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(converter::toPostResponseDto)
                .orElseThrow(() -> new NotFoundException("post not found"));
    }

    @Transactional
    public Long insert(PostRequestDto requestDto) throws NotFoundException {
        User writer = userRepository.findById(requestDto.getWriterId())
                .orElseThrow(() -> new NotFoundException("writer not found"));

        Post post = converter.toPostEntity(requestDto, writer);
        postRepository.save(post);

        return post.getId(); // OSIV
    }

    @Transactional
    public void update(Long id, PostRequestDto requestDto) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception("post not found"));

        post.updateContent(requestDto.getContent());
        post.updateTitle(requestDto.getTitle());

    }

    @Transactional
    public void delete(Long id) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception("post not found"));

        post.delete();
        postRepository.delete(post);
    }

    @Transactional
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
