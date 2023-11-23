package com.devcourse.springbootboardjpahi.service;

import com.devcourse.springbootboardjpahi.domain.Post;
import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PageResponse;
import com.devcourse.springbootboardjpahi.dto.PostDetailResponse;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.dto.UpdatePostRequest;
import com.devcourse.springbootboardjpahi.repository.PostRepository;
import com.devcourse.springbootboardjpahi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse create(CreatePostRequest request) {
        User author = userRepository.findById(request.userId())
                .orElseThrow();
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(author)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow();

        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostDetailResponse updateById(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow();

        post.updateTitle(request.title());
        post.updateContent(request.content());

        return PostDetailResponse.from(post);
    }

    public PageResponse<PostResponse> getPage(Pageable pageable) {
        Page<PostResponse> page = postRepository.findAll(pageable)
                .map(PostResponse::from);

        return PageResponse.from(page);
    }
}
