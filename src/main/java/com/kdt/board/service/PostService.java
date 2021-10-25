package com.kdt.board.service;

import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.post.PostRequest;
import com.kdt.board.dto.post.PostResponse;
import com.kdt.board.repository.CommentRepository;
import com.kdt.board.repository.PostRepository;
import com.kdt.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Optional<Post> findOnePost(Long postId) {
        return postRepository.findById(postId);
    }

    @Transactional
    public List<Post> findAllByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    // note : service가 service를 부르는 건 좋지 않기 때문에 주의
    @Transactional
    public Long createPost(PostRequest postRequest) {
        User user = userRepository.findByName(postRequest.getUserName())
                .orElseGet(() -> User.builder()
                        .name(postRequest.getUserName())
                        .build());
        Post newPost = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .user(user)
                .build();
        userRepository.save(user);
        return postRepository.save(newPost).getId();
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post postToUpdate = postRepository.findById(postId).get();
        User newUser = postToUpdate.getUser().changeName(postRequest.getUserName());
        postToUpdate.changePost(postRequest.getTitle(),
                postRequest.getContent(),
                newUser);
        return new PostResponse(postRepository.save(postToUpdate));
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

}
