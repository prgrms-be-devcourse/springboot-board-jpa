package com.homework.springbootboard.service;

import com.homework.springbootboard.converter.PostConverter;
import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.exception.PostNotFoundException;
import com.homework.springbootboard.model.Post;
import com.homework.springbootboard.repository.PostRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public Long save(PostDto postDto) {
        return postRepository.save(postConverter.convertPost(postDto)).getId();
    }

    public Long update(Long id, PostDto postDto) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Can't find Post"));
        findPost.updatePost(postDto);
        return findPost.getId();
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long id) {
        return postRepository.findById(id).map(postConverter::convertPostDto)
                .orElseThrow(() -> new PostNotFoundException("Can't find Post."));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDto);
    }
}
