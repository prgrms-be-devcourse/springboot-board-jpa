package com.juwoong.springbootboardjpa.post.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.juwoong.springbootboardjpa.post.application.model.PostDto;
import com.juwoong.springbootboardjpa.post.domain.Post;
import com.juwoong.springbootboardjpa.post.domain.repository.PostRepository;
import com.juwoong.springbootboardjpa.user.application.UserService;
import com.juwoong.springbootboardjpa.user.domain.User;

@Service
@Transactional
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    public PostService(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public PostDto createPost(Long userId, String postTitle, String postContent) {
        User searchedUser = userService.searchByIdForPost(userId);

        Post post = new Post(searchedUser, postTitle, postContent);

        Post createdPost = postRepository.save(post);

        return toDto(createdPost);
    }

    public PostDto updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id).get();

        post.update(title, content);

        post = postRepository.save(post);

        return toDto(post);

    }

    public PostDto getPostById(Long id) {
        Post searchedPost = postRepository.findById(id).get();

        return toDto(searchedPost);
    }

    public Page<PostDto> getAllPosts(Pageable pageable) {

        return postRepository.findAll(pageable).map(this::toDto);
    }

    private PostDto toDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getId(), post.getTitle(), post.getContent(),
            post.getCreatedAt(),
            post.getUpdatedAt());
    }

}
