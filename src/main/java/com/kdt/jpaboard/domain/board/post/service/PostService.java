package com.kdt.jpaboard.domain.board.post.service;

import com.kdt.jpaboard.domain.board.post.PostRepository;
import com.kdt.jpaboard.domain.board.post.Posts;
import com.kdt.jpaboard.domain.board.post.converter.PostConverter;
import com.kdt.jpaboard.domain.board.post.dto.CreatePostDto;
import com.kdt.jpaboard.domain.board.post.dto.PostDto;
import com.kdt.jpaboard.domain.board.post.dto.UpdatePostDto;
import com.kdt.jpaboard.domain.board.user.converter.UserConverter;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final UserConverter userConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter, UserConverter userConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
        this.userConverter = userConverter;
    }

    @Transactional
    public Long save(CreatePostDto postDto) {
        Posts posts = postConverter.convertCreatePost(postDto);
        Posts savedPost = postRepository.save(posts);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertReadPostDto);
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long id) throws NotFoundException {
        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("찾는 게시물이 없습니다."));
        return postConverter.convertReadPostDto(posts);
    }

    @Transactional
    public Long update(Long id, UpdatePostDto updatePostDto) throws NotFoundException {

        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("업데이트할 게시물을 찾지 못했습니다."));

        posts.update(
                updatePostDto.getTitle(),
                updatePostDto.getContent());

        Posts save = postRepository.save(posts);
        return save.getId();
    }

    @Transactional
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
