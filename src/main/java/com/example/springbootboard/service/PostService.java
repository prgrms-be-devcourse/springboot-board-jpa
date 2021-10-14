package com.example.springbootboard.service;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long save(String title, String content, String uuid) throws NotFoundException {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("유효하지 않은 사용자 입니다."));
        Post post = postRepository.save(new Post(title, content, user));
        return post.getId();
    }

    @Transactional
    public Long update(Long id, String title, String content) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글 id 입니다."));
        post.update(title, content);
        Post updatedPost = postRepository.save(post);
        return updatedPost.getId();
    }

    @Transactional
    public Post findOne(Long postId) throws NotFoundException {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글 id 입니다."));
    }

    @Transactional
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
