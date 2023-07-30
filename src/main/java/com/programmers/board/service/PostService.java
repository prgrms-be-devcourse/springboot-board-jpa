package com.programmers.board.service;

import com.programmers.board.domain.Post;
import com.programmers.board.domain.User;
import com.programmers.board.dto.PostDto;
import com.programmers.board.repository.PostRepository;
import com.programmers.board.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createPost(Long userId, String title, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다"));
        Post post = new Post(title, content, "hseong3243", user);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageRequest);
        return posts.map(PostDto::from);
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long postId) {
        Post post = findPostOrElseThrow(postId);
        return PostDto.from(post);
    }

    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post post = findPostOrElseThrow(postId);
        post.update(title, content);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findPostOrElseThrow(postId);
        postRepository.delete(post);
    }

    private Post findPostOrElseThrow(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다"));
        return post;
    }
}
