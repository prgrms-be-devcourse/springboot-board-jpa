package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PostService {

    @Transactional(readOnly = true)
    PostDTO.Response findById(long id);

    Long save(PostDTO.Save postDTO);

    void update(long id, String title, String contents);

    @Transactional(readOnly = true)
    Page<PostDTO.Response> findAll(Pageable pageable); // read only, write only read / write -> jpa transational readonly
}
