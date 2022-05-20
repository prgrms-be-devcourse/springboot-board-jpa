package com.prgrms.board.post.service;

import com.prgrms.board.common.exception.NotFoundException;
import com.prgrms.board.post.domain.Post;
import com.prgrms.board.post.dto.PostRequest;
import com.prgrms.board.post.dto.PostResponse;
import com.prgrms.board.post.repository.PostRepository;
import com.prgrms.board.user.domain.User;
import com.prgrms.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostResponse insert(PostRequest postRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.getPosts().canPost();
        Post post = postRequest.toPost(user);
        return PostResponse.of(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public Slice<PostResponse> findAllByKeyword(String keyword, Pageable pageable) {
        return postRepository.findPostPageable(keyword, pageable);
    }

    @Transactional(readOnly = true)
    public PostResponse findOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NotFoundException::new);
        return PostResponse.of(post);
    }

    public PostResponse updateOne(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(NotFoundException::new);
        post.changeTitle(postRequest.getTitle());
        post.changeContent(postRequest.getContent());
        return PostResponse.of(postRepository.save(post));
    }

    public void deleteOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NotFoundException::new);
        postRepository.delete(post);
    }
}
