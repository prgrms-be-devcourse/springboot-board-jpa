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
    public Post write(String title, String content, String writerName) {
        User writer = findUserByName(writerName);
        Post post = new Post(title, content, writer);

        return postRepository.save(post);
    }

    @Override
    public Post edit(Long postId, String title, String content, String editorName) {
        User editor = findUserByName(editorName);
        Post post = findPostById(postId);

        checkEditable(post, editor);

        return post.edit(title, content);
    }

    private User findUserByName(String name){
        return userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }

    private void checkEditable(Post post, User editor) {
        if (!post.isWrittenBy(editor)) {
            throw new IllegalArgumentException();
        }
    }
}
