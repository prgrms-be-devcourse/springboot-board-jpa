package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.example.board.dto.PostDto.*;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Response save(Request request) {
        User user = userRepository.findById(request.userId()).orElseThrow(NoSuchElementException::new);
        Post post = Post.toEntity(user, request);
        return toResponse(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public Page<Response> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostDto::toResponse);
    }

    @Transactional(readOnly = true)
    public Response findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return toResponse(post);
    }

    public void update(Long postId, Request request) {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        post.changePost(request.title(), request.content());
    }
}
