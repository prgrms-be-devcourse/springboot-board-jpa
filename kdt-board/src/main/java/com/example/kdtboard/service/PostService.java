package com.example.kdtboard.service;

import com.example.kdtboard.converter.PostConverter;
import com.example.kdtboard.domain.Post;
import com.example.kdtboard.repository.PostRepository;
import com.example.kdtboard.domain.User;
import com.example.kdtboard.repository.UserRepository;
import com.example.kdtboard.dto.CreatePostRequest;
import com.example.kdtboard.dto.PostDto;
import com.example.kdtboard.dto.UpdatePostRequest;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostConverter postConverter;

    @Transactional
    public Long save(CreatePostRequest postRequest) throws NotFoundException {
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("Invalid User, UserId: " + postRequest.getUserId()));
        Post post = postConverter.convertPostWithUser(postRequest,user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public PostDto findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

    }
    @Transactional
    public Long updateOne(UpdatePostRequest updatePostRequest) throws NotFoundException {
        Post findPost = postRepository.findById(updatePostRequest.getPostId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));
        findPost.changePost(updatePostRequest.getTitle(),updatePostRequest.getContent());
        return findPost.getId();
    }
}
