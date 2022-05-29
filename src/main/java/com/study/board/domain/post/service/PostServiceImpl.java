package com.study.board.domain.post.service;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.repository.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    @Override
    public Post findById(Long postId) {
        return findPostById(postId);
    }

    @Override
    public Post write(String title, String content, String writerLoginId) {
        User writer = findUserByLoginId(writerLoginId);
        Post post = new Post(title, content, writer);

        return postRepository.save(post);
    }

    @Override
    public Post edit(Long postId, String title, String content, String editorLoginId) {
        User editor = findUserByLoginId(editorLoginId);
        Post post = findPostById(postId);

        return post.edit(title, content, editor.getId());
    }

    private User findUserByLoginId(String name) {
        return userRepository.findByLoginId(name).orElseThrow(IllegalArgumentException::new);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }


}
