package com.prgrms.springbootboardjpa.post.application;

import com.prgrms.springbootboardjpa.post.domain.Post;
import com.prgrms.springbootboardjpa.post.domain.PostRepository;
import com.prgrms.springbootboardjpa.post.dto.*;
import com.prgrms.springbootboardjpa.post.exception.NotFoundByIdPostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponse save(PostInsertRequest postInsertRequest) {
        Post resultPost = postRepository.save(postInsertRequest.toPost());
        return new PostResponse(resultPost);
    }

    @Transactional
    public PostResponse update(PostUpdateRequest postUpdateRequest) {
        Post foundPost = postRepository.findById(postUpdateRequest.getPostId()).orElseThrow(() -> new NotFoundByIdPostException(postUpdateRequest.getPostId()));
        foundPost.changePost(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        return new PostResponse(foundPost);
    }

    public PostResponse findById(long postId) {
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundByIdPostException(postId));
        return new PostResponse(foundPost);
    }

    public PostAllResponse findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses = posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

        return new PostAllResponse(postResponses);
    }

    public PostOnePage findOnePagePost(int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);

        Page<Post> posts = postRepository.findPostPage(pageRequest);

        List<PostResponse> postForPages = posts.stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
        int bookCount = postForPages.size();
        int allPageCount = posts.getTotalPages();

        return new PostOnePage(bookCount, allPageCount, postForPages);
    }

}
