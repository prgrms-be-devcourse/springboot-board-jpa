package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.PostDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostService {

    @Transactional
    Optional<PostDTO> findById(String id);

    @Transactional
    List<PostDTO> findAll();

    @Transactional
    String save(PostDTO postDTO);

    @Transactional
    void update(String id, String title, String contents);
}
