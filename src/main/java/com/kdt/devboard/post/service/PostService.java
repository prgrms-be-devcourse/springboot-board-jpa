package com.kdt.devboard.post.service;

import com.kdt.devboard.post.domain.Post;
import com.kdt.devboard.post.domain.PostDto;
import com.kdt.devboard.post.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostDto::new);
    }

    @Transactional
    public PostDto findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(PostDto::new)
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));

    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postDto.toEntity();

        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public PostDto update(PostDto postDto) throws NotFoundException {
        Post findPost = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));
        findPost.changeInfo(postDto.getTitle(), postDto.getContent());

        return new PostDto(findPost);
    }

    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));
        postRepository.delete(post);
    }

}
