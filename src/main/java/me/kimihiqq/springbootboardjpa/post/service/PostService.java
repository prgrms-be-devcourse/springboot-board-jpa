package me.kimihiqq.springbootboardjpa.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.kimihiqq.springbootboardjpa.post.domain.Post;
import me.kimihiqq.springbootboardjpa.post.dto.request.PostCreateRequest;
import me.kimihiqq.springbootboardjpa.post.dto.request.PostUpdateRequest;
import me.kimihiqq.springbootboardjpa.post.dto.response.PostResponse;
import me.kimihiqq.springbootboardjpa.post.repository.PostRepository;
import me.kimihiqq.springbootboardjpa.user.domain.User;
import me.kimihiqq.springbootboardjpa.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::new);
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse save(PostCreateRequest requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + requestDto.getUserId()));
        Post post = requestDto.toEntity(user);
        post = postRepository.save(post);
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.updatePost(requestDto.getTitle(), requestDto.getContent());
        return new PostResponse(post);
    }
}
