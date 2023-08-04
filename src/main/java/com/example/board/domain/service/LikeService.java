package com.example.board.domain.service;

import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.PostLike;
import com.example.board.domain.entity.User;
import com.example.board.domain.entity.repository.LikeRepository;
import com.example.board.dto.like.LikePostRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    @Transactional
    public Long createLikePost(LikePostRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUserId());
        Post post = postService.getPost(requestDto.getPostId());
        PostLike postLike = requestDto.toEntity(user, post);

        return likeRepository.save(postLike).getId();
    }

}
