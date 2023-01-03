package com.example.springbootboard.service;

import com.example.springbootboard.dto.Converter;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.entity.Post;
import com.example.springbootboard.exception.PostNotFoundException;
import com.example.springbootboard.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.springbootboard.dto.Converter.*;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Post createPost(PostDto dto) {
        return repository.save(dtoToPost(dto));
    }

    @Transactional(readOnly = true)
    public PostDto findPostById(Long id) throws Exception{
        return repository.findById(id)
                .map(Converter::postToDto)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<PostDto> findAllPosts(){
        return repository.findAll()
                .stream()
                .map(Converter::postToDto)
                .toList();
    }

    @Transactional
    public PostDto updatePost(PostDto dto) throws Exception{
        Optional<Post> retrievedPost = repository.findById(dto.getId());
        if(retrievedPost.isEmpty())
            throw new PostNotFoundException();
        retrievedPost.get().changeContent(dto.getContent());
        retrievedPost.get().changeTitle(dto.getTitle());
        return dto;
    }
}
