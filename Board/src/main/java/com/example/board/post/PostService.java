package com.example.board.post;

import javassist.NotFoundException;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostDto updatePost(PostDto postDto) throws NotFoundException;
    void deletePost(PostDto postDto) throws NotFoundException;
    PostDto findById(Long id) throws NotFoundException;

    List<PostDto> findAll();
}
