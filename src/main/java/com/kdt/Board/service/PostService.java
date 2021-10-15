package com.kdt.Board.service;

import com.kdt.Board.dto.PostRequest;
import com.kdt.Board.dto.PostResponse;
import com.kdt.Board.entity.Post;
import com.kdt.Board.repository.PostRepository;
import com.kdt.Board.utils.ConversionDtoEntity;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final ConversionDtoEntity conversion;


    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(conversion::toPostDto);
    }

    public PostResponse getPost(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(conversion::toPostDto)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public Long writePost(Long userId, PostRequest postRequest) {
        final Post savedPost = postRepository.save(conversion.toPost(userId, postRequest));
        return savedPost.getId();
    }

    @Transactional
    public Long editPost(Long userId, Long postId, PostRequest postRequest) throws AuthenticationException {
        Post post = postRepository.findById(postId).get();
        if (userId != post.getUser().getId()) throw new AuthenticationException("권한이 없습니다.");
        post.editPost(postRequest);
        return post.getId();
    }
}
