package com.study.board.domain.post.service;

import com.study.board.domain.exception.PostNotFoundException;
import com.study.board.domain.exception.UserNotFoundException;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return findPostById(postId);
    }

    public Post write(String title, String content, Long writerId) {
        User user = findUserById(writerId);

        return postRepository.save(new Post(title, content, user));
    }

    public Post edit(Long postId, String title, String content, Long editorId) {
        User editor = findUserById(editorId);
        Post post = findPostById(postId);

        post.checkUpdatable(editor);

        return post.update(title, content);
    }

    private User findUserById(Long writerId) {
        return userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException(writerId));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

}
