package org.prgrms.board.domain.post.Service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.board.domain.post.domain.Post;
import org.prgrms.board.domain.post.exception.PostException;
import org.prgrms.board.domain.post.mapper.PostMapper;
import org.prgrms.board.domain.post.repository.PostRepository;
import org.prgrms.board.domain.post.request.PostCreateRequest;
import org.prgrms.board.domain.post.request.PostUpdateRequest;
import org.prgrms.board.domain.post.response.PostSearchResponse;
import org.prgrms.board.domain.user.response.UserSearchResponse;
import org.prgrms.board.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.prgrms.board.global.exception.ErrorCode.POST_NOT_EXIST;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserService userService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Transactional(readOnly = true)
    public Page<PostSearchResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postMapper::toSearchResponse);
    }

    @Transactional(readOnly = true)
    public PostSearchResponse findById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_EXIST));
        return postMapper.toSearchResponse(post);
    }

    @Transactional
    public long save(PostCreateRequest createRequest, long userId) {
        UserSearchResponse userResponse = userService.findById(userId);
        Post post = postMapper.toEntity(createRequest, userResponse.getId());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public long update(long postId, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_EXIST));
        post.update(updateRequest.getTitle(), updateRequest.getContent());
        log.info("Post updated, id: {}", postId);
        return postId;
    }

    @Transactional
    public long delete(long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_EXIST));
        postRepository.deleteById(postId);
        log.info("Post deleted, id: {}", postId);
        return postId;
    }

}
