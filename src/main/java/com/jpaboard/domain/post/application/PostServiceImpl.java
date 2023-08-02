package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.Post;
import com.jpaboard.domain.post.PostConverter;
import com.jpaboard.domain.post.dto.request.PostCreateRequest;
import com.jpaboard.domain.post.dto.request.PostSearchRequest;
import com.jpaboard.domain.post.dto.request.PostUpdateRequest;
import com.jpaboard.domain.post.dto.response.PostPageResponse;
import com.jpaboard.domain.post.dto.response.PostResponse;
import com.jpaboard.domain.post.infrastructure.PostRepository;
import com.jpaboard.domain.user.User;
import com.jpaboard.domain.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    // Repository를 주입 받는 게 좋은지, Service를 주입 받는게 좋은지
    private final UserRepository userRepository;

    @Transactional
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

    public PostPageResponse findPosts(Pageable pageable) {
        Page<PostResponse> posts = postRepository.findAll(pageable)
                .map(PostConverter::convertEntityToResponse);
        return PostConverter.convertEntityToPageResponse(posts);
    }

    public PostPageResponse findPostsByCondition(PostSearchRequest request, Pageable pageable) {
        Page<PostResponse> posts = postRepository.findAllByCondition(request.title(), request.content(), request.keyword(), pageable)
                .map(PostConverter::convertEntityToResponse);
        return PostConverter.convertEntityToPageResponse(posts);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.update(request.title(), request.content());
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}
