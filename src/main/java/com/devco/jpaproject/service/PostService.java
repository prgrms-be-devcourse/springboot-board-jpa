package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.PostDeleteRequestDto;
import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.controller.dto.PostUpdateRequestDto;
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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final Converter converter;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByPages(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::toPostResponseDto);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) throws PostNotFoundException {
        return postRepository.findById(id)
                .map(converter::toPostResponseDto)
                .orElseThrow(() -> new PostNotFoundException("postId: " + id + " not found"));
    }

    @Transactional
    public Long insert(PostRequestDto requestDto) throws UserNotFoundException {
        User writer = userRepository.findById(requestDto.getWriterId())
                .orElseThrow(() -> new UserNotFoundException("writerId: " + requestDto.getWriterId() + " not found"));

        Post post = converter.toPostEntity(requestDto, writer);

        writer.addPost(post); // 연관관계 편의 메소드
        postRepository.save(post);

        return post.getId(); // OSIV false
    }

    @Transactional
    public void update(PostUpdateRequestDto dto) throws PostNotFoundException, UserAndPostNotMatchException {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("postId: " + dto.getPostId() + " not found"));

        if(post.getWriter().getId() != dto.getWriterId()) {
            throw new UserAndPostNotMatchException(
                    String.format("writer({}) and post({}) are not matched", dto.getWriterId(), dto.getPostId())
            );
        }

        post.updateTitleAndContent(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void deleteOne(PostDeleteRequestDto dto) throws PostNotFoundException, UserAndPostNotMatchException {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("postId: " + dto.getPostId() + " not found"));

        if(post.getWriter().getId() != dto.getWriterId()) {
            throw new UserAndPostNotMatchException(
                    String.format("writer({}) and post({}) are not matched", dto.getWriterId(), dto.getPostId())
            );
        }

        post.getWriter().deletePost(post);

        postRepository.delete(post);
    }
}
