package com.maenguin.kdtbbs.service;

import com.maenguin.kdtbbs.converter.BBSConverter;
import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostAddResDto;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.dto.PostListDto;
import com.maenguin.kdtbbs.exception.PostNotFoundException;
import com.maenguin.kdtbbs.exception.UserNotFoundException;
import com.maenguin.kdtbbs.repository.PostRepository;
import com.maenguin.kdtbbs.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final BBSConverter bbsConverter;

    public PostService(PostRepository postRepository, UserRepository userRepository, BBSConverter bbsConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.bbsConverter = bbsConverter;
    }

    public PostListDto getAllPosts(Pageable pageable) {
        Page<PostDto> page = postRepository.findAll(pageable)
            .map(bbsConverter::convertToPostDto);
        return new PostListDto(page);
    }

    @Transactional
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(" for id:" + postId));
        post.increaseView();
        return bbsConverter.convertToPostDto(post);
    }

    @Transactional
    public PostAddResDto savePost(PostAddDto postAddDto) {
        Long userId = postAddDto.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(" for id: " + userId));
        Post post = bbsConverter.convertToPost(postAddDto);
        post.changeUser(user);
        Post entity = postRepository.save(post);
        return new PostAddResDto(entity.getPostId());
    }

    @Transactional
    public PostAddResDto editPost(Long postId, PostAddDto postAddDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(" for id:" + postId));
        post.editPost(postAddDto.getTitle(), postAddDto.getContent());
        return new PostAddResDto(post.getPostId());
    }
}
