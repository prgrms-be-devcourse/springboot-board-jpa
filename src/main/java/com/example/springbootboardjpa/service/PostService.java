package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.converter.PostConverter;
import com.example.springbootboardjpa.domain.Post;
import com.example.springbootboardjpa.dto.PostDto;
import com.example.springbootboardjpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public long create(PostDto postDto) {
        Post result = postRepository.save(PostConverter.toPost(postDto));
        return result.getId();
    }

    @Transactional
    public Page<PostDto> readAll(Pageable pageable) {
        Page<PostDto> result = postRepository.findAll(pageable).map(PostConverter::toPostDto);
        return result;
    }

    @Transactional
    public PostDto read(Long id) {
        PostDto result = PostConverter.toPostDto(postRepository.findById(id).orElseThrow(()-> new RuntimeException("not exist")));
        return result;
    }

    @Transactional
    public String update(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("not exist"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCratedAt(LocalDateTime.now());
        return "Successfully update id "+ post.getId();
    }

    @Transactional
    public String delete(Long id) {
        postRepository.deleteById(id);
        return "Successfully delete id "+ id;
    }
}
