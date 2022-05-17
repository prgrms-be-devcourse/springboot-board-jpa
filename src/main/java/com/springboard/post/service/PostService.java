package com.springboard.post.service;

import com.springboard.common.exception.FindFailException;
import com.springboard.post.util.PostConverter;
import com.springboard.post.dto.CreatePostRequest;
import com.springboard.post.dto.CreatePostResponse;
import com.springboard.post.dto.FindPostResponse;
import com.springboard.post.dto.UpdatePostRequest;
import com.springboard.post.entity.Post;
import com.springboard.post.repository.PostRepository;
import com.springboard.user.entity.User;
import com.springboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    public FindPostResponse findOne(Long id) {
        Post post = postRepository.findById(id).orElseThrow(FindFailException::new);
        return postConverter.getFindResponseFrom(post);
    }

    public Page<FindPostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::getFindResponseFrom);
    }

    @Transactional
    public CreatePostResponse save(CreatePostRequest request) {
        User user = userRepository.getById(request.userId());
        Post post = postRepository.save(postConverter.getPostFrom(request, user));
        return postConverter.getCreateResponseFrom(post);
    }

    @Transactional
    public FindPostResponse updateOne(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(FindFailException::new);
        post.setTitle(request.title());
        post.setContent(request.content());
        Post response = postRepository.save(post);
        return postConverter.getFindResponseFrom(response);
    }

    @Transactional
    public void deleteOne(Long id) {
        postRepository.deleteById(id);
    }
}
