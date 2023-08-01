package com.programmers.jpaboard.service;

import com.programmers.jpaboard.domain.Post;
import com.programmers.jpaboard.domain.User;
import com.programmers.jpaboard.dto.request.PostCreateRequest;
import com.programmers.jpaboard.dto.response.PostCreateResponse;
import com.programmers.jpaboard.dto.response.PostFindResponse;
import com.programmers.jpaboard.exception.BusinessException;
import com.programmers.jpaboard.repository.PostRepository;
import com.programmers.jpaboard.repository.UserRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostCreateResponse createPost(PostCreateRequest request) {
        Long userId = request.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("게시물을 생성하는 사용자의 ID가 올바르지 않습니다.", Map.of("userId", userId)));

        Post post = request.toEntity();
        post.updateUser(user);

        Post savedPost = postRepository.save(post);

        return PostCreateResponse.of(savedPost.getId());
    }

    public List<PostFindResponse> findAllPosts() {
        return postRepository.findAll().stream()
                .map(PostFindResponse::fromEntity)
                .toList();
    }

    public PostFindResponse findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 ID의 게시물이 존재하지 않습니다.", Map.of("postId", id)));

        return PostFindResponse.fromEntity(post);
    }
}
