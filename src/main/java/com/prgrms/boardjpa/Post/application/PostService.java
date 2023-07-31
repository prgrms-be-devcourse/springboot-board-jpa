package com.prgrms.boardjpa.Post.application;

import com.prgrms.boardjpa.Post.domain.Post;
import com.prgrms.boardjpa.Post.domain.PostRepository;
import com.prgrms.boardjpa.Post.dto.request.PostCreateRequest;
import com.prgrms.boardjpa.Post.dto.request.PostUpdateRequest;
import com.prgrms.boardjpa.Post.dto.response.PostListResponse;
import com.prgrms.boardjpa.Post.dto.response.PostResponse;
import com.prgrms.boardjpa.User.domain.User;
import com.prgrms.boardjpa.User.domain.UserRepository;
import com.prgrms.boardjpa.global.ErrorCode;
import com.prgrms.boardjpa.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse create(PostCreateRequest createRequest) {
        User user = userRepository.findById(createRequest.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Post post = createRequest.toEntity();
        Post savedPost = postRepository.save(post);

        savedPost.updateUser(user);

        return PostResponse.create(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse findOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return PostResponse.create(post);
    }

    @Transactional(readOnly = true)
    public PostListResponse findAll(Pageable pageable) {
        Page<Post> postList = postRepository.findAll(pageable);
        List<PostResponse> postResponseList = postList
                .stream()
                .map(PostResponse::create)
                .toList();

        return PostListResponse.create(postResponseList);
    }

    @Transactional
    public PostResponse update(Long postId, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        post.update(updateRequest);
        post.updateUser(user);

        return PostResponse.create(post);
    }
}