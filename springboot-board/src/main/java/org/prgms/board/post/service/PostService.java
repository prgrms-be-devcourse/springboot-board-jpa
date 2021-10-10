package org.prgms.board.post.service;

import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.exception.NotFoundException;
import org.prgms.board.exception.NotMatchException;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long addPost(Long userId, PostRequest postRequest) {
        User user = getUser(userId);

        return postRepository.save(Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .writer(user)
                .build()).getId();
    }

    @Transactional
    public Long modifyPost(Long userId, Long postId, PostRequest postRequest) {
        User user = getUser(userId);
        Post post = getPost(postId);

        if (!user.equals(post.getWriter())) {
            throw new NotMatchException("자신의 게시글만 수정할 수 있습니다.");
        }

        post.changeInfo(postRequest.getTitle(), postRequest.getContent());
        return post.getId();
    }

    @Transactional
    public void removePost(Long userId, Long postId) {
        User user = getUser(userId);
        Post post = getPost(postId);

        if (!user.equals(post.getWriter())) {
            throw new NotMatchException("자신의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(getPost(postId));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponse::new);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPostByUser(Pageable pageable, Long userId) {
        User user = getUser(userId);
        return postRepository.findAllByWriter(pageable, user)
                .map(PostResponse::new);
    }

    @Transactional(readOnly = true)
    public PostResponse getOnePost(Long postId) {
        return new PostResponse(getPost(postId));
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }
}
