package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.PostResponse;
import com.prgrms.jpa.controller.dto.post.PostsResponse;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.domain.User;
import com.prgrms.jpa.exception.EntityNotFoundException;
import com.prgrms.jpa.exception.ExceptionMessage;
import com.prgrms.jpa.repository.PostRepository;
import com.prgrms.jpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.jpa.utils.ToDtoMapper.toPostDto;
import static com.prgrms.jpa.utils.ToDtoMapper.toPostsDto;
import static com.prgrms.jpa.utils.ToEntityMapper.toPost;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final String USER = "사용자";
    private static final String POST = "게시글";

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public long create(CreatePostRequest createPostRequest) {
        User user = userRepository.findById(createPostRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), USER)));
        Post post = postRepository.save(toPost(createPostRequest, user));
        return post.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponse findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return toPostsDto(page.getContent(), page.getTotalPages(), page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse findById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), POST)));
        return toPostDto(post);
    }

    @Override
    @Transactional
    public void update(long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), POST)));
        post.change(updatePostRequest.getTitle(), updatePostRequest.getContent());
    }
}
