package com.kdt.prgrms.board.service;

import com.kdt.prgrms.board.dto.PostAddRequest;
import com.kdt.prgrms.board.dto.PostResponse;
import com.kdt.prgrms.board.dto.PostUpdateRequest;
import com.kdt.prgrms.board.entity.post.Post;
import com.kdt.prgrms.board.entity.post.PostRepository;
import com.kdt.prgrms.board.entity.user.User;
import com.kdt.prgrms.board.entity.user.UserRepository;
import com.kdt.prgrms.board.exception.custom.AccessDeniedException;
import com.kdt.prgrms.board.exception.custom.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addPost(PostAddRequest postAddRequest) {

        if (postAddRequest == null) {
            throw new IllegalArgumentException();
        }

        long userId = postAddRequest.getUserId();

        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        postRepository.save(postAddRequest.toEntity(user));
    }

    public List<PostResponse> getPosts() {

        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse getPostById(long id) {

        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);

        return PostResponse.from(post);
    }

    @Transactional
    public void updatePostById(long id, PostUpdateRequest postUpdateRequest) {

        if (postUpdateRequest == null) {
            throw new IllegalArgumentException();
        }

        long requestUserId = postUpdateRequest.getUserId();
        User user = userRepository.findById(requestUserId).orElseThrow(NotFoundException::new);
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!post.isSameUser(user)) {
            throw new AccessDeniedException();
        }

        post.updatePost(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        postRepository.save(post);
    }
}
