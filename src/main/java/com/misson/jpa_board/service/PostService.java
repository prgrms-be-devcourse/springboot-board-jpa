package com.misson.jpa_board.service;

import com.misson.jpa_board.converter.PostConverter;
import com.misson.jpa_board.domain.Post;
import com.misson.jpa_board.domain.User;
import com.misson.jpa_board.dto.PostCreateRequest;
import com.misson.jpa_board.dto.PostDto;
import com.misson.jpa_board.repository.PostRepository;
import com.misson.jpa_board.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostConverter postConverter;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostConverter postConverter, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long save(PostCreateRequest postDto) throws NotFoundException {
        User user = userRepository
                .findById(postDto.getUserId())
                .orElseThrow(() -> new NotFoundException("아이디와 일치하는 회원이 없습니다."));
        Post post = postConverter.convertToPostCreateRequest(postDto, user);
        Post postEntity = postRepository.save(post);
        return postEntity.getId();
    }

    @Transactional
    public PostDto postFindById(Long id) throws NotFoundException {
        log.info("postFindById : {}", id);
        return postRepository.findById(id).
                map(postConverter::convertPostDto).
                orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
    }

    @Transactional
    public PostDto postChange(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("아이디와 일치하는 게시물이 없습니다."));
        post.changePost(postDto.getTitle(), postDto.getContent());
        return postConverter.convertPostDto(post);
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }
}
