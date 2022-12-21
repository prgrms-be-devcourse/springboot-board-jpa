package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<PostDTO> findById(String id);

    List<PostDTO> findAll();

    String save(PostDTO postDTO);

    void update(String id, String title, String contents);
}
