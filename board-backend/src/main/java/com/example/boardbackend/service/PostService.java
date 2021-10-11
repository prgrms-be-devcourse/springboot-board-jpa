package com.example.boardbackend.service;

import com.example.boardbackend.dto.converter.DtoConverter;
import com.example.boardbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final DtoConverter dtoConverter;
}
