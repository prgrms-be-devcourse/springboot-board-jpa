package com.juwoong.springbootboardjpa.post.application;

import java.util.NoSuchElementException;
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
        User user = userService.getUserByIdForPost(userId);

        Post post = new Post(user, postTitle, postContent);

        post = postRepository.save(post);

        return toDto(post);
    }

    public PostDto updatePost(Long id, String title, String content) {
        Post post = findById(id);

        post.update(title, content);

        post = postRepository.save(post);

        return toDto(post);
    }

    @Transactional(readOnly = true)
    public PostDto getPostById(Long id) {
        Post post = findById(id);

        return toDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::toDto);
    }

    private Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("게시물이 존재하지 않습니다."));
    }

    private PostDto toDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getId(), post.getTitle(), post.getContent(),
            post.getCreatedAt(),
            post.getUpdatedAt());
    }

}
