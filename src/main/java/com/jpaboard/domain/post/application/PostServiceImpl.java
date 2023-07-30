package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.Post;
import com.jpaboard.domain.post.PostConverter;
import com.jpaboard.domain.post.dto.PostCreateRequest;
import com.jpaboard.domain.post.dto.PostResponse;
import com.jpaboard.domain.post.dto.PostUpdateRequest;
import com.jpaboard.domain.post.infrastructure.PostRepository;
import com.jpaboard.domain.user.User;
import com.jpaboard.domain.user.infrastructure.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    // Repository를 주입 받는 게 좋은지, Service를 주입 받는게 좋은지
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Long createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(IllegalArgumentException::new);
        Post post = PostConverter.convertRequestToEntity(request, user);
        postRepository.save(post);
        return post.getId();
    }

    public PostResponse findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return PostConverter.convertEntityToResponse(post);
    }

    public Page<PostResponse> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertEntityToResponse);
    }

    public Page<PostResponse> findPostByTitle(String title, Pageable pageable) {
        return postRepository.findAllByTitleContaining(title, pageable)
                .map(PostConverter::convertEntityToResponse);
    }

    public Page<PostResponse> findPostByContent(String content, Pageable pageable) {
        return postRepository.findAllByContentContaining(content, pageable)
                .map(PostConverter::convertEntityToResponse);
    }

    public Page<PostResponse> findByKeyword(String keyword, Pageable pageable) {
        return postRepository.findAllByTitleContainingOrContentContaining(keyword, pageable)
                .map(PostConverter::convertEntityToResponse);
    }

    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.update(request);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}
