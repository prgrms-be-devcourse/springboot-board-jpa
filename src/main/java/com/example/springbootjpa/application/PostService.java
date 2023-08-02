package com.example.springbootjpa.application;


import com.example.springbootjpa.domain.post.Post;
import com.example.springbootjpa.domain.post.PostRepository;
import com.example.springbootjpa.domain.user.User;
import com.example.springbootjpa.domain.user.UserRepository;
import com.example.springbootjpa.ui.dto.post.PostFindResponse;
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

    public List<PostFindResponse> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostFindResponse::from)
                .stream()
                .toList();
    }

    public PostFindResponse findPost(Long postId) {
        Post post = findByPostId(postId);

        return PostFindResponse.from(post);
    }

    @Transactional
    public long updatePost(long postId, String title, String content) {
        Post post = findByPostId(postId);
        post.update(title, content);

        return post.getId();
    }

    private Post findByPostId(Long postId) {
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
}
