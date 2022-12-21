package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultPostService implements PostService {
    @Override
    public Optional<PostDTO> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<PostDTO> findAll() {
        return null;
    }

    @Override
    public String save(PostDTO postDTO) {
        return null;
    }

    @Override
    public void update(String id, String title, String contents) {

    }
}
