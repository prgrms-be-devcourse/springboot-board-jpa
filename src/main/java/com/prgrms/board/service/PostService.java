package com.prgrms.board.service;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.Users;
import com.prgrms.board.dto.post.PostResponse;
import com.prgrms.board.dto.post.PostSaveRequest;
import com.prgrms.board.dto.post.PostUpdateRequest;
import com.prgrms.board.dto.post.SimplePostResponse;
import com.prgrms.board.global.util.Paging;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(PostSaveRequest saveRequest) {
        Users user = userRepository.findById(saveRequest.getUserId())
                .orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        Post savedPost = postRepository.save(
                new Post(user, saveRequest.getTitle(), saveRequest.getContent())
        );
        return PostResponse.fromEntity(savedPost);
    }

    @Transactional(readOnly = true)
    public List<SimplePostResponse> findPostsByCriteria(int page, Long userId) {

        if(userId != null) {
            Page<Post> postPage = postRepository.findByUser(userId, Paging.createPageRequest(page));
            return SimplePostResponse.fromEntities(postPage.getContent());
        }
        Page<Post> postPage = postRepository.findAllWithUser(Paging.createPageRequest(page));
        return SimplePostResponse.fromEntities(postPage.getContent());
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("없는 게시글입니다."));
        return PostResponse.fromEntity(post);
    }

    @Transactional
    public PostResponse updatePost(PostUpdateRequest updateRequest, Long postId) {
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("없는 게시글입니다."));
        foundPost.updatePost(
                updateRequest.getTitle(), updateRequest.getContent()
        );
        return PostResponse.fromEntity(foundPost);
    }

    @Transactional
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}