package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostService {

    @Transactional
    PostDTO.Response findById(long id) throws NotFoundException;

    @Transactional
    List<PostDTO> findAll();

    @Transactional
    long save(PostDTO.Save postDTO);

    @Transactional
    void update(long id, String title, String contents) throws NotFoundException;
}
