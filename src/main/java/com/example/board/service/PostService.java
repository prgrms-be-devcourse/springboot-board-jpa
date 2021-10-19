package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostRequest request) {
        Post post = postConverter.convertToPost(request);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertToResponse);
    }

    // TODO: 공통적인 예외 메세지 따로 뽑아 관리하기
    public PostResponse findById(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertToResponse)
                .orElseThrow(() -> new PostNotFoundException(
                        MessageFormat.format("Post Not Found. There exists no such post with the given ID {0}", id)
                ));
    }

    @Transactional
    public Long editPost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(
                MessageFormat.format("Post Not Found. There exists no such post with the given ID {0}", postId)
        ));
        post.updateTitle(request.getTitle());
        post.updateContent(request.getContent());
        return post.getId();
    }
}
