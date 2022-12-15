package com.assignment.board.service;

import com.assignment.board.converter.PostConverter;
import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
import com.assignment.board.dto.post.PostUpdateDto;
import com.assignment.board.entity.Post;
import com.assignment.board.entity.User;
import com.assignment.board.repository.PostRepository;
import com.assignment.board.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostConverter postConverter;

    public PostResponseDto createPost(PostRequestDto postRequestDto) throws NotFoundException {

        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));

        Post post = postConverter.convertPost(postRequestDto);
        post.setCreatedBy(user.getName());
        user.addPost(post);

        Post savedPost = postRepository.save(post);

        return postConverter.convertPostDto(savedPost);
    }

    public PostResponseDto getPostById(Long postId) throws NotFoundException {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        return postConverter.convertPostDto(post);
    }

    public Page<PostResponseDto> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    public PostResponseDto updatePost(PostUpdateDto postUpdateDto) throws NotFoundException {

        Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());

        return postConverter.convertPostDto(post);
    }

}
