package com.study.board.domain.post.service;

import com.study.board.domain.exception.PostNotFoundException;
import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }
}
