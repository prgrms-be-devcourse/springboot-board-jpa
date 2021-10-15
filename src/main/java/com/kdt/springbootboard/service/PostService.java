package com.kdt.springbootboard.service;

import com.kdt.springbootboard.converter.PostConverter;
import com.kdt.springbootboard.domain.Post;
import com.kdt.springbootboard.dto.PostDto;
import com.kdt.springbootboard.exception.PostNotFoundException;
import com.kdt.springbootboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Transactional
    public Long save(PostDto postDto) {
        return postRepository.save(postConverter.convertPost(postDto)).getId();
    }

    @Transactional
    public Long update(Long id, PostDto postDto) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Can't find Post"));
        findPost.updatePost(postDto);
        return findPost.getId();
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new PostNotFoundException("Can't find Post"));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDto);
    }

    @Transactional
    public void deletePost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Can't find Post"));
        postRepository.deleteById(findPost.getId());
    }

}
