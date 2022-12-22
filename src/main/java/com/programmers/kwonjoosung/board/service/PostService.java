package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.model.dto.PostResponse;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PostService {

    PostResponse findPostByPostId(Long postId);

    List<PostResponse> findPostByUserId(Long userId);

    List<PostResponse> findAllPost();

    List<PostResponse> findAllPost(PageRequest pageRequest);

    IdResponse createPost(Long userId, CreatePostRequest request);

    void updatePost(Long postId, UpdatePostRequest request);
}
