package com.assignment.board.service;

import com.assignment.board.domain.Post;
import com.assignment.board.dto.DtoConverter;
import com.assignment.board.dto.PostDto;
import com.assignment.board.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DtoConverter dtoConverter;

    @Transactional
    public Long save(PostDto postDto) {
        Post post = dtoConverter.convertPost(postDto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(dtoConverter::convertPostDto);
    }

    @Transactional
    public PostDto findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(dtoConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다"));
    }

    @Transactional
    public PostDto update(PostDto postDto) throws NotFoundException {
        Post findPost = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다"));

        findPost.postUpdate(postDto.getTitle(), postDto.getContent());

        return dtoConverter.convertPostDto(findPost);

    }

}
