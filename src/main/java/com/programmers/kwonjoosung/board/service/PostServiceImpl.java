package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.User;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.model.dto.PostResponse;
import com.programmers.kwonjoosung.board.repository.PostRepository;
import com.programmers.kwonjoosung.board.model.Post;
import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import com.programmers.kwonjoosung.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private static final String NOT_FOUND_USER_MESSAGE = "해당 아이디로 조회되는 유저가 없습니다.";
    private static final String NOT_FOUND_POST_MESSAGE = "조회되는 게시글이 없습니다.";
    private static final String NOT_FOUND_POST_BY_USER_ID_MESSAGE = "해당 아이디로 조회되는 게시글이 없습니다.";
    private static final String NOT_FOUND_POST_BY_POST_ID_MESSAGE = "해당 게시글이 존재하지 않습니다.";

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public PostResponse findPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .map(PostResponse::new)
                .orElseThrow(() ->
                        new IllegalArgumentException(NOT_FOUND_POST_BY_POST_ID_MESSAGE));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> findPostByUserId(Long userId) {
        List<Post> posts = postRepository.findByUser_Id(userId);
        isEmpty(posts,NOT_FOUND_POST_BY_USER_ID_MESSAGE);
        return posts.stream().map(PostResponse::new).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> findAllPost() {
        List<Post> posts = postRepository.findAll();
        isEmpty(posts,NOT_FOUND_POST_MESSAGE);
        return posts.stream().map(PostResponse::new).toList();
    }

    @Transactional
    @Override
    public IdResponse createPost(Long userId, CreatePostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_USER_MESSAGE));
        Post post = postRepository.save(request.toEntity());
        post.setUser(user);
        return new IdResponse(post.getId());
    }

    @Transactional
    @Override
    public void updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new IllegalArgumentException(NOT_FOUND_POST_BY_POST_ID_MESSAGE));

        if(request.getTitle() != null) {
            post.changeTitle(request.getTitle());
        }

        if(request.getContent() != null) {
            post.changeContent(request.getContent());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> findAllPost(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest)
                .stream()
                .map(PostResponse::new)
                .toList();
    }

    private static void isEmpty(List<?> list, String message) {
        if(list == null || list.size() == 0 ) {
            throw new IllegalArgumentException(message);
        }
    }
}
