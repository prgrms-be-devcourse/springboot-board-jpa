package com.programmers.board.service;

import com.programmers.board.constant.AuthConst;
import com.programmers.board.domain.Post;
import com.programmers.board.domain.User;
import com.programmers.board.dto.PostDto;
import com.programmers.board.exception.AuthorizationException;
import com.programmers.board.repository.PostRepository;
import com.programmers.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final String NO_SUCH_USER = "존재하지 않는 회원입니다";
    private static final String NO_SUCH_POST = "존재하지 않은 게시글입니다";

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(Long userId, String title, String content) {
        User user = findUserOrElseThrow(userId);
        Post post = new Post(title, content, user);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAllWithUser(pageRequest);
        return posts.map(PostDto::from);
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long postId) {
        Post post = postRepository.findByIdWithUser(postId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_POST));
        return PostDto.from(post);
    }

    @Transactional
    public void updatePost(Long loginUserId, Long postId, String title, String content) {
        User loginUser = findUserOrElseThrow(loginUserId);
        Post post = findPostOrElseThrow(postId);
        checkAuthority(post, loginUser);
        post.update(title, content);
    }

    @Transactional
    public void deletePost(Long postId, Long loginUserId) {
        User loginUser = findUserOrElseThrow(loginUserId);
        Post post = findPostOrElseThrow(postId);
        checkAuthority(post, loginUser);
        postRepository.delete(post);
    }

    private Post findPostOrElseThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_POST));
    }

    private User findUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_USER));
    }

    private void checkAuthority(Post post, User loginUser) {
        if (!post.isWriter(loginUser)) {
            throw new AuthorizationException(AuthConst.NO_AUTHORIZATION);
        }
    }
}
