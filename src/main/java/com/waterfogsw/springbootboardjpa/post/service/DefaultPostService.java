package com.waterfogsw.springbootboardjpa.post.service;

import com.waterfogsw.springbootboardjpa.common.exception.AuthenticationException;
import com.waterfogsw.springbootboardjpa.common.exception.ResourceNotFoundException;
import com.waterfogsw.springbootboardjpa.post.entity.Post;
import com.waterfogsw.springbootboardjpa.post.repository.PostRepository;
import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class DefaultPostService implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    public DefaultPostService(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void addPost(long userId, Post post) {
        Assert.isTrue(userId > 0, "User id should be positive");
        Assert.notNull(post, "Post should not be null");

        final var user = userService.getOne(userId);
        post.updateAuthor(user);
        postRepository.save(post);
    }

    @Override
    public Post getOne(long id) {
        Assert.isTrue(id > 0, "Post id should be positive");

        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    public List<Post> getAll(Pageable pageable) {
        Assert.notNull(pageable, "Pageable shout not be null");
        return postRepository.findWithPagination(pageable);
    }


    @Override
    @Transactional
    public void updatePost(long userId, long postId, Post post) {
        Assert.isTrue(userId > 0, "User id should be positive");
        Assert.isTrue(postId > 0, "Post id should be positive");
        Assert.notNull(post, "Post should not be null");

        final var user = userService.getOne(userId);
        final var targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        checkAuthor(user, targetPost.getUser());

        targetPost.update(post.getTitle(), post.getContent());
    }

    private void checkAuthor(User currentUser, User author) {
        if (!currentUser.equals(author)) {
            throw new AuthenticationException("Current user is not writer");
        }
    }
}
