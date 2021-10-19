package com.poogle.board.service.post;

import com.poogle.board.controller.post.PostRequest;
import com.poogle.board.error.NotFoundException;
import com.poogle.board.model.post.Post;
import com.poogle.board.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post write(Post post) {
        return insert(post);
    }

    private Post insert(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Post> findPost(Long id) {
        return postRepository.findById(id);
    }

    public Post modify(Long id, PostRequest post) {
        Post foundPost = findPostById(id);
        foundPost.update(post.getTitle(), post.getContent());
        return insert(foundPost);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException(Post.class, id));
    }
}
