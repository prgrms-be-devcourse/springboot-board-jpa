package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.post.request.PostCreateRequest;
import com.example.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.example.springbootboardjpa.dto.post.response.PostResponse;
import com.example.springbootboardjpa.entity.Post;
import com.example.springbootboardjpa.entity.User;
import com.example.springbootboardjpa.repository.PostRepository;
import com.example.springbootboardjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(PostCreateRequest postCreateRequest) {
        User user = userRepository.findById(postCreateRequest.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));


        Post post = postCreateRequest.toEntity();
        post.updateUser(user);
        postRepository.save(post);
        return PostResponse.fromEntity(post);
    }

    public List<PostResponse> findAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::fromEntity)
                .toList();
    }

    public PostResponse findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        return PostResponse.fromEntity(post);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("수정하려는 게시글이 존재하지 않습니다."));

        post.updateTitle(updateRequest.getTitle());
        post.updateContent(updateRequest.getContent());

        return PostResponse.fromEntity(post);
    }

    @Transactional
    public void deletePostById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new NoSuchElementException("삭제하려는 게시글을 찾지 못했습니다.");
        }

        postRepository.deleteById(id);
    }
}
