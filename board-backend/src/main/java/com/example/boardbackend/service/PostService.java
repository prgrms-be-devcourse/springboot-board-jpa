package com.example.boardbackend.service;

import com.example.boardbackend.dto.DtoConverter;
import com.example.boardbackend.repository.PostRepository;
import com.example.boardbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final DtoConverter dtoConverter;
}
