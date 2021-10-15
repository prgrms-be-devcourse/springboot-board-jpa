package com.programmers.springboard.service;

import com.programmers.springboard.error.PostNotFoundException;
import com.programmers.springboard.converter.PostConverter;
import com.programmers.springboard.dto.PostDto;
import com.programmers.springboard.dto.PostResponseDto;
import com.programmers.springboard.model.Post;
import com.programmers.springboard.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.programmers.springboard.error.ErrorCode.POSTS_NOT_FOUND;

@Service
public class PostService {
    private PostRepository postRepository;
    private PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto dto) {
        Post post = postConverter.convertPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    public PostResponseDto findOne(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new PostNotFoundException(POSTS_NOT_FOUND));
    }

    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public Long update(PostDto dto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(POSTS_NOT_FOUND));
        post.update(dto.getTitle(), dto.getContent(), postConverter.convertUser(dto.getUserDto()));
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(POSTS_NOT_FOUND));
        postRepository.delete(post);
    }

}
