package com.example.springbootboardjpa.post.service;

import com.example.springbootboardjpa.domain.Post;
import com.example.springbootboardjpa.domain.PostRepository;
import com.example.springbootboardjpa.domain.User;
import com.example.springbootboardjpa.domain.UserRepository;
import com.example.springbootboardjpa.post.converter.PostConverter;
import com.example.springbootboardjpa.post.dto.CreatePostRequest;
import com.example.springbootboardjpa.post.dto.PostDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public PostDto insert(CreatePostRequest createPostRequest) {
        User user = userRepository.getById(createPostRequest.getUserId());
        Post post = Post.builder()
                .content(createPostRequest.getContent())
                .title(createPostRequest.getTitle())
                .user(user)
                .build();
        Post entity = postRepository.save(post);
        return postConverter.convertPostDto(entity);
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        List<PostDto> list = postRepository.findAll()
                .stream().map(postConverter::convertPostDto).toList();
        return new PageImpl<>(list, pageable, list.size());
    }

    @Transactional
    public PostDto findById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() ->
                        new NotFoundException("게시물을 찾을 수 없습니다."));
    }

    @Transactional
    public PostDto update(PostDto postDto) {
        Post post = postRepository.getById(postDto.getId())
                .toBuilder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        Post update = postRepository.save(post);
        return postConverter.convertPostDto(update);
    }

    @Transactional
    public Long deleteById(Long id) {
        postRepository.deleteById(id);
        return id;
    }
}
