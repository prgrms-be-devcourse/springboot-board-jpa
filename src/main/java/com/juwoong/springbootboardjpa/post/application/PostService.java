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
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    public PostDto createPost(Long userId, String postTitle, String postContent) {
        User searchedUser = userService.searchByIdForPost(userId);

        Post post = Post.builder()
            .user(searchedUser)
            .title(postTitle)
            .content(postContent)
            .build();

        Post createdPost = postRepository.save(post);

        return toDto(createdPost);
    }

    public PostDto editPost(Long postId, String postTitle, String postContent) {
        Post searchedPost = postRepository.findById(postId).get();

        searchedPost.update(postTitle, postContent);

        Post updatedPost = postRepository.save(searchedPost);

        return toDto(updatedPost);
    }

    public PostDto searchById(Long id) {
        Post searchedPost = postRepository.findById(id).get();

        return toDto(searchedPost);
    }

    public Page<PostDto> searchAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(this::toDto);
    }

    private PostDto toDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getId(), post.getTitle(), post.getContent(),
            post.getCreatedAt(),
            post.getUpdatedAt());
    }

}
