package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.request.PostCreateRequest;
import com.example.springbootboardjpa.dto.request.PostUpdateRequest;
import com.example.springbootboardjpa.dto.response.PostResponse;
import com.example.springbootboardjpa.entity.Post;
import com.example.springbootboardjpa.repository.PostRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private PostRepository postRepository;

    @Transactional
    public PostResponse create(PostCreateRequest postCreateRequest) {
        Post post = postRepository.save(postCreateRequest.toEntity());
        return PostResponse.fromEntity(post);
    }

    public List<PostResponse> findByAll() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::fromEntity)
                .toList();
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        return PostResponse.fromEntity(post);
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("수정하려는 게시글이 존재하지 않습니다."));

        post.updateTitle(updateRequest.getTitle());
        post.updateContent(updateRequest.getContent());

        return PostResponse.fromEntity(post);
    }

    //삭제
    @Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new NoSuchElementException("삭제하려는 게시글을 찾지 못했습니다.");
        }

        postRepository.deleteById(id);
    }
}
