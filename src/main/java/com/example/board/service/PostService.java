package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.post.CreatePostRequest;
import com.example.board.dto.request.post.PageCondition;
import com.example.board.dto.request.post.PostSearchCondition;
import com.example.board.dto.request.post.UpdatePostRequest;
import com.example.board.dto.response.CustomResponseStatus;
import com.example.board.dto.response.PageResponse;
import com.example.board.dto.response.PostResponse;
import com.example.board.exception.CustomException;
import com.example.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Long createPost(Long id, CreatePostRequest requestDto) {
        final User user = userService.getAvailableUser(id);
        final Post post = postRepository.save(PostConverter.toPost(requestDto, user));
        return post.getId();
    }

    @Transactional(readOnly = true)
    public PageResponse<PostResponse> getPosts(PostSearchCondition condition, PageCondition pageCondition) {
        Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
        Page<PostResponse> posts = postRepository.findAll(condition, pageable).map(PostConverter::toPostResponse);
        return PageResponse.of(posts);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(CustomResponseStatus.POST_NOT_FOUND));
        return PostConverter.toPostResponse(post);
    }

    public void updatePost(Long userId, Long postId, UpdatePostRequest requestDto) {
        final Post post = postRepository.findByIdWithAuthor(postId).orElseThrow(() -> new CustomException(CustomResponseStatus.POST_NOT_FOUND));

        if (!post.isSameAuthorId(userId))
            throw new CustomException(CustomResponseStatus.AUTHOR_NOT_MATCH);

        post.update(requestDto.title(), requestDto.content());
    }

    public void deletePost(Long userId, Long postId) {
        final Post post = postRepository.findByIdWithAuthor(postId).orElseThrow(() -> new CustomException(CustomResponseStatus.POST_NOT_FOUND));

        if (!post.isSameAuthorId(userId))
            throw new CustomException(CustomResponseStatus.AUTHOR_NOT_MATCH);

        postRepository.delete(post);
    }

}
