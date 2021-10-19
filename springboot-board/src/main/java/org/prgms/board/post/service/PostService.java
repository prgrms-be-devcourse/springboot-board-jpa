package org.prgms.board.post.service;

import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.common.exception.NotMatchException;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.user.dto.UserIdRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long writePost(PostRequest postRequest) {
        User user = activeUser(postRequest.getUserId());

        return postRepository.save(
            Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .writer(user)
                .build()
        ).getId();
    }

    @Transactional
    public Long modifyPost(Long postId, PostRequest postRequest) {
        Post post = validate(postRequest.getUserId(), postId);
        post.changeInfo(postRequest.getTitle(), postRequest.getContent());
        return post.getId();
    }

    @Transactional
    public void removePost(Long postId, UserIdRequest userIdRequest) {
        Post post = validate(userIdRequest.getUserId(), postId);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(PostResponse::new);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByUser(Pageable pageable, Long id) {
        User user = activeUser(id);
        return postRepository.findAllByWriter(pageable, user)
            .map(PostResponse::new);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        return new PostResponse(findPost(postId));
    }

    private Post validate(Long userId, Long postId) {
        User user = activeUser(userId);
        Post post = findPost(postId);

        if (!user.equals(post.getWriter())) {
            throw new NotMatchException(String.format("해당 %d번 게시글을 작성한 사용자가 아닙니다.", postId));
        }

        return post;
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("해당 %d번 게시글을 찾을 수 없습니다.", id)));
    }

    private User activeUser(Long id) {
        return userRepository.findByIdAndDeleted(id, false)
            .orElseThrow(() -> new NotFoundException(String.format("해당 %d번 사용자를 찾을 수 없습니다.", id)));
    }
}
