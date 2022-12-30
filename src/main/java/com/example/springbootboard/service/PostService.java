package com.example.springbootboard.service;

import com.example.springbootboard.entity.Post;
import com.example.springbootboard.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> findAll(){
        return repository.findAll();
    }

    public Optional<Post> findById(Long id){
        return repository.findById(id);
    }
}
