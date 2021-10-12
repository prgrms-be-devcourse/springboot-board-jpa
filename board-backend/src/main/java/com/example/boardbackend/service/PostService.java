package com.example.boardbackend.service;

import com.example.boardbackend.domain.Post;
import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.converter.DtoConverter;
import com.example.boardbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;
    private final DtoConverter dtoConverter;

    @Transactional
    public PostDto savePost(PostDto postDto){
        Post post = dtoConverter.convetToPostEntity(postDto);
        System.out.println(postDto.toString());
        System.out.println(post.toString());
        Post saved = postRepository.save(post);
        return dtoConverter.convertToPostDto(saved);
    }

    public List<PostDto> findPostsByCreatedBy(Long createdBy){
        return postRepository.findByCreatedBy(createdBy).stream()
                .map(post -> dtoConverter.convertToPostDto(post))
                .collect(Collectors.toList());
    }

}
