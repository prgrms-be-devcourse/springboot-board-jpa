package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto.Response save(PostDto.Request request) {
        User user = userRepository.findById(request.userId()).orElseThrow(NoSuchElementException::new);
        Post post = Post.toEntity(user, request);
        return PostDto.toResponse(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public List<PostDto.Response> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDto::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PostDto.Response findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return PostDto.toResponse(post);
    }

    public void update(Long postId, PostDto.Request request) {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        post.changePost(request.title(), request.content());
    }
}
