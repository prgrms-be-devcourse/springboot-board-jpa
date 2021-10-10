package com.homework.springbootboard.service;

import com.homework.springbootboard.converter.PostConverter;
import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.repository.PostRepository;
import javassist.NotFoundException;
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

    public Long save(PostDto postDto) {
        return postRepository.save(postConverter.convertPost(postDto)).getId();
    }

    public Long update(PostDto postDto) throws NotFoundException {
        return postRepository.findById(postDto.getId())
                .map(post -> postConverter.convertPost(postDto))
                .orElseThrow(() -> new NotFoundException("Can't find Post."))
                .getId();
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long id) throws NotFoundException {
        return postRepository.findById(id).map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("Can't find Post."));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDto);
    }
}
