package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.post.*;
import com.example.board.dto.response.PageResponse;
import com.example.board.dto.response.PostResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Long createPost(CreatePostRequest requestDto) {
        final User user = userService.getAvailableUser(requestDto.authorId());
        final Post post = postRepository.save(PostConverter.toPost(requestDto, user));
        return post.getId();
    }

    @Transactional(readOnly = true)
    public PageResponse<PostResponse> getPosts(PostSearchCondition condition, PageCondition pageCondition) {
        Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());

        List<PostResponse> posts = postRepository.findAll(condition, pageable).stream()
                .map(PostConverter::toPostResponse)
                .collect(Collectors.toList());

        return PageResponse.of(PageableExecutionUtils.getPage(posts, pageable, () -> postRepository.countAll(condition)));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return PostConverter.toPostResponse(post);
    }

    public void updatePost(Long id, UpdatePostRequest requestDto) {
        final Post post = postRepository.findByIdWithAuthor(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.isSameAuthorId(requestDto.authorId()))
            throw new CustomException(ErrorCode.AUTHOR_NOT_MATCH);

        post.update(requestDto.title(), requestDto.content());
    }

    public void deletePost(Long id, DeletePostRequest requestDto) {
        final Post post = postRepository.findByIdWithAuthor(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.isSameAuthorId(requestDto.authorId()))
            throw new CustomException(ErrorCode.AUTHOR_NOT_MATCH);

        postRepository.delete(post);
    }

}
