package com.kdt.board.domain.post.service;

import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.post.repository.PostRepository;
import com.kdt.board.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new BaseException("해당 게시글이 없습니다")
        );
    }

    public Post updatePost(Long id, Post post) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 게시글이 없습니다");
        }

        return postRepository.save(post);
    }
}
