package com.example.springbootjpa.application;


import com.example.springbootjpa.domain.post.Post;
import com.example.springbootjpa.domain.post.PostRepository;
import com.example.springbootjpa.domain.user.User;
import com.example.springbootjpa.domain.user.UserRepository;
import com.example.springbootjpa.ui.dto.post.PostDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public List<PostDto> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostDto::from)
                .stream()
                .toList();
    }

    public PostDto findPost(Long postId) {
        Post post = findById(postId);

        return PostDto.from(post);
    }

    private Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(
                        () -> new EntityNotFoundException("post doesn't exist")
                );
    }

    @Transactional
    public long createPost(long userId, String title, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("user doesn't exist")
                );

        Post post = new Post(title, content, user);
        postRepository.save(post);

        return post.getId();
    }

    @Transactional
    public long updatePost(long postId, String title, String content) {
        Post post = findById(postId);
        post.update(title, content);

        return post.getId();
    }
}
