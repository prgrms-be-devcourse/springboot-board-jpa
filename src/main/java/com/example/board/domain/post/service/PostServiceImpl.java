package com.example.board.domain.post.service;

import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.domain.user.entity.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.validator.PostValidator;
import com.example.board.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.board.domain.post.dto.PostDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Page<SinglePostResponse> pagingPost(Pageable pageable) {
    }

    @Override
    public SinglePostResponse getPost(long postId) {
        Optional<Post> mayBePost = postRepository.findById(postId);
        Post validatedPost = PostValidator.validateOptionalPostExists(mayBePost);
        return SinglePostResponse.toResponse(validatedPost);
    }

    @Override
    @Transactional
    public SinglePostResponse post(CreatePostRequest createPostRequest) {

    }

    @Override
    @Transactional
    public SinglePostResponse updatePost(long postId, UpdatePostRequest updatePostRequest) {

    }
}
