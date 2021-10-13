package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.PostDeleteRequestDto;
import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.domain.Post;
import com.devco.jpaproject.domain.User;
import com.devco.jpaproject.exception.PostNotFoundException;
import com.devco.jpaproject.exception.UserAndPostNotMatchException;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.repository.PostRepository;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.converter.Converter;
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
    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(converter::toPostResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByPages(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::toPostResponseDto);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) throws PostNotFoundException {
        return postRepository.findById(id)
                .map(converter::toPostResponseDto)
                .orElseThrow(() -> new PostNotFoundException("postId: " + id + "not found"));
    }

    @Transactional
    public Long insert(PostRequestDto requestDto) throws UserNotFoundException {
        User writer = userRepository.findById(requestDto.getWriterId())
                .orElseThrow(() -> new UserNotFoundException("writerId: " + requestDto.getWriterId() + " not found"));

        Post post = converter.toPostEntity(requestDto, writer);
        postRepository.save(post);

        return post.getId(); // OSIV
    }

    @Transactional
    public void update(Long id, PostRequestDto requestDto) throws PostNotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("postId: " + id + "not found"));

        post.updateContent(requestDto.getContent());
        post.updateTitle(requestDto.getTitle());

    }

    @Transactional
    public void deleteOne(PostDeleteRequestDto dto) throws PostNotFoundException, UserNotFoundException, UserAndPostNotMatchException {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("postId: " + dto.getPostId() + "not found"));

        User writer = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new UserNotFoundException("writerId: " + dto.getWriterId() + " not found"));

        boolean result = writer.getPosts().stream()
                .anyMatch(p -> p.getId() == post.getId());

        if (!result)
            throw new UserAndPostNotMatchException(String.format("postId: %d, writerId: %d", dto.getPostId(), dto.getWriterId()));

        post.delete();
        postRepository.delete(post);
    }
}
