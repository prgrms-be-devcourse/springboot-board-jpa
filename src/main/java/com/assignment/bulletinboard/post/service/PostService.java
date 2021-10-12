package com.assignment.bulletinboard.post.service;

import com.assignment.bulletinboard.post.PostRepository;
import com.assignment.bulletinboard.post.converter.PostConverter;
import com.assignment.bulletinboard.post.dto.PostDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostConverter postConverter;
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostDto postDto) {
        return postRepository.save(postConverter.convertToPost(postDto)).getId();
    }

    @Transactional
    public Long update(PostDto postDto) throws NotFoundException {
        return postRepository.findById(postDto.getId())
                .map(post -> postConverter.convertToPost(postDto))
                .orElseThrow(() -> new NotFoundException("Not Found Post ID : " + postDto.getId())).getId();
    }

    @Transactional(readOnly = true)
    public PostDto findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertToPostDto)
                .orElseThrow(() -> new NotFoundException("Not Found Post ID : " + id));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertToPostDto);
    }
}
