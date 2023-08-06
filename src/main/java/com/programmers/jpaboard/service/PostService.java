package com.programmers.jpaboard.service;

import com.programmers.jpaboard.domain.Post;
import com.programmers.jpaboard.domain.User;
import com.programmers.jpaboard.dto.post.request.PostCreateRequest;
import com.programmers.jpaboard.dto.post.request.PostUpdateRequest;
import com.programmers.jpaboard.dto.post.response.PostDetailResponse;
import com.programmers.jpaboard.dto.post.response.PostIdResponse;
import com.programmers.jpaboard.exception.BusinessException;
import com.programmers.jpaboard.repository.PostRepository;
import com.programmers.jpaboard.repository.UserRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostIdResponse createPost(PostCreateRequest request) {
        Long userId = request.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("게시물을 생성하는 사용자의 ID가 올바르지 않습니다.", Map.of("userId", userId)));

        Post post = request.toEntity();
        post.updateUser(user);

        Post savedPost = postRepository.save(post);

        return PostIdResponse.from(savedPost.getId());
    }

    public List<PostDetailResponse> findAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDetailResponse::fromEntity)
                .toList();
    }

    public PostDetailResponse findPostById(Long id) {
        return PostDetailResponse.fromEntity(getPostById(id));
    }

    @Transactional
    public PostIdResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = getPostById(id);
        post.updateTitle(request.getTitle());
        post.updateContent(request.getContent());

        return PostIdResponse.from(post.getId());
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.delete(getPostById(id));
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 ID의 게시물이 존재하지 않습니다.", Map.of("postId", id)));
    }
}
